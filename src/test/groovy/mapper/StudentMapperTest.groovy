package mapper

import az.ingress.dao.entity.StudentEntity
import az.ingress.model.request.StudentRequest
import io.github.benas.randombeans.api.EnhancedRandom
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

import static az.ingress.mapper.StudentMapper.STUDENT_MAPPER
import static az.ingress.model.enums.EntityState.CREATED
import static io.github.benas.randombeans.EnhancedRandomBuilder.aNewEnhancedRandom

class StudentMapperTest extends Specification {
    EnhancedRandom random = aNewEnhancedRandom()

    def "TestMapRequestToEntity"() {
        given:
        def request = random.nextObject(StudentRequest)

        when:
        def entity = STUDENT_MAPPER.mapRequestToEntity(request)

        then:
        request.firstName == entity.firstName
        request.lastName == entity.lastName
        request.age == entity.age
        CREATED == entity.status
    }

    def "TestMapEntityToResponse"() {
        given:
        def entity = random.nextObject(StudentEntity)

        when:
        def response = STUDENT_MAPPER.mapEntityToResponse(entity)

        then:
        entity.id == response.id
        entity.firstName == response.firstName
        entity.lastName == response.lastName
        entity.age == response.age
    }

    def "TestBuildPageableResponse"() {
        given:
        def pageNumber = 0
        def pageSize = 10
        def total = 1L
        def pageable = PageRequest.of(pageNumber, pageSize)
        def entity = random.nextObject(StudentEntity)
        def pageOfStudents = new PageImpl([entity], pageable, total)

        when:
        def pageableResponse = STUDENT_MAPPER.buildPageableResponse(pageOfStudents)

        then:
        entity.id == pageableResponse.content[pageNumber].id
        entity.firstName == pageableResponse.content[pageNumber].firstName
        entity.lastName == pageableResponse.content[pageNumber].lastName
        entity.age == pageableResponse.content[pageNumber].age
        ++pageNumber == pageableResponse.totalPages
        total == pageableResponse.totalElements
        !pageableResponse.hasNextPage
    }
}