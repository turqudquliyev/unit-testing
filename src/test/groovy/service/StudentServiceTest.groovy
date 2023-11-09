package service

import az.ingress.dao.entity.StudentEntity
import az.ingress.dao.repository.StudentRepository
import az.ingress.exception.NotFoundException
import az.ingress.model.request.CustomPageRequest
import az.ingress.model.request.StudentRequest
import az.ingress.service.concrete.StudentServiceHandler
import io.github.benas.randombeans.api.EnhancedRandom
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

import static az.ingress.model.enums.EntityState.DELETED
import static az.ingress.model.enums.EntityState.UPDATED
import static io.github.benas.randombeans.EnhancedRandomBuilder.aNewEnhancedRandom

class StudentServiceTest extends Specification {
    EnhancedRandom random = aNewEnhancedRandom()
    StudentRepository studentRepository
    StudentServiceHandler studentService

    def setup() {
        studentRepository = Mock()
        studentService = new StudentServiceHandler(studentRepository)
    }

    def "TestSave success case"() {
        given:
        def request = random.nextObject(StudentRequest)
        def entity = random.nextObject(StudentEntity)

        when:
        def response = studentService.save(request)

        then:
        1 * studentRepository.save(_ as StudentEntity) >> entity
        entity.id == response.id
        entity.firstName == response.firstName
        entity.lastName == response.lastName
        entity.age == response.age
    }

    def "TestGetById success case"() {
        given:
        def id = random.nextLong()
        def entity = random.nextObject(StudentEntity)

        when:
        def response = studentService.getById(id)

        then:
        1 * studentRepository.findById(id) >> Optional.of(entity)
        entity.id == response.id
        entity.firstName == response.firstName
        entity.lastName == response.lastName
        entity.age == response.age
    }

    def "TestGetById StudentNotFound case"() {
        given:
        def id = random.nextLong()

        when:
        studentService.getById(id)

        then:
        1 * studentRepository.findById(id) >> Optional.empty()
        NotFoundException ex = thrown()
        ex.message == "STUDENT_NOT_FOUND"
    }

    def "TestGetAll success case"() {
        given:
        def pageNumber = 0
        def pageSize = 10
        def total = 1L
        def pageRequest = CustomPageRequest.of(pageNumber, pageSize)
        def pageable = PageRequest.of(pageNumber, pageSize)
        def entity = random.nextObject(StudentEntity)
        def pageOfStudents = new PageImpl([entity], pageable, total)

        when:
        def pageableResponse = studentService.getAll(pageRequest)

        then:
        1 * studentRepository.findAll(pageable) >> pageOfStudents
        entity.id == pageableResponse.students[pageNumber].id
        entity.firstName == pageableResponse.students[pageNumber].firstName
        entity.lastName == pageableResponse.students[pageNumber].lastName
        entity.age == pageableResponse.students[pageNumber].age
        ++pageNumber == pageableResponse.totalPages
        total == pageableResponse.totalElements
        !pageableResponse.hasNextPage
    }

    def "TestUpdateById success case"() {
        given:
        def id = random.nextLong()
        def request = random.nextObject(StudentRequest)
        def entity = random.nextObject(StudentEntity)

        when:
        studentService.updateById(id, request)

        then:
        1 * studentRepository.findById(id) >> Optional.of(entity)
        request.firstName == entity.firstName
        request.lastName == entity.lastName
        request.age == entity.age
        UPDATED == entity.status
        1 * studentRepository.save(entity)
    }

    def "TestUpdateById StudentNotFound case"() {
        given:
        def id = random.nextLong()
        def request = random.nextObject(StudentRequest)

        when:
        studentService.updateById(id, request)

        then:
        1 * studentRepository.findById(id) >> Optional.empty()
        NotFoundException ex = thrown()
        ex.message == "STUDENT_NOT_FOUND"
    }

    def "TestDeleteById success case"() {
        given:
        def id = random.nextLong()
        def entity = random.nextObject(StudentEntity)

        when:
        studentService.deleteById(id)

        then:
        1 * studentRepository.findById(id) >> Optional.of(entity)
        entity.status == DELETED
        1 * studentRepository.save(entity)
    }

    def "TestDeleteById StudentNotFound case"() {
        given:
        def id = random.nextLong()

        when:
        studentService.deleteById(id)

        then:
        1 * studentRepository.findById(id) >> Optional.empty()
        NotFoundException ex = thrown()
        ex.message == "STUDENT_NOT_FOUND"
    }
}