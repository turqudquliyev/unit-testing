package controller

import az.ingress.controller.StudentController
import az.ingress.exception.CustomExceptionHandler
import az.ingress.model.request.CustomPageRequest
import az.ingress.model.request.StudentRequest
import az.ingress.model.response.PageableStudentResponse
import az.ingress.model.response.StudentResponse
import az.ingress.service.abstraction.StudentService
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*

class StudentControllerTest extends Specification {
    StudentService studentService
    StudentController studentController
    MockMvc mockMvc

    def setup() {
        studentService = Mock()
        studentController = new StudentController(studentService)
        mockMvc = MockMvcBuilders.standaloneSetup(studentController)
                .setControllerAdvice(CustomExceptionHandler.class)
                .build()
    }

    def "TestCreate success case"() {
        given:
        def url = "/v1/students"
        def request = StudentRequest.builder()
                .firstName("firstName")
                .lastName("lastName")
                .age(11)
                .build()
        def response = StudentResponse.builder()
                .id(1L)
                .firstName("firstName")
                .lastName("lastName")
                .age(11)
                .build()
        def jsonRequest =
                """
                    {
                      "firstName": "firstName",
                      "lastName": "lastName",
                      "age": 11
                    }
                """
        def expectedResponse =
                """
                    {
                      "id": 1,
                      "firstName": "firstName",
                      "lastName": "lastName",
                      "age": 11
                    }
                """

        when:
        def jsonResponse = mockMvc.perform(
                post(url)
                        .contentType(APPLICATION_JSON)
                        .content(jsonRequest)
        ).andReturn()

        then:
        1 * studentService.save(request) >> response
        jsonResponse.response.status == CREATED.value()
        JSONAssert.assertEquals(expectedResponse.toString(), jsonResponse.response.contentAsString.toString(), true)
    }

    def "TestRetrieveById success case"() {
        given:
        def id = 1L
        def url = "/v1/students/$id"
        def response = StudentResponse.builder()
                .id(1L)
                .firstName("firstName")
                .lastName("lastName")
                .age(11)
                .build()
        def expectedResponse =
                """
                    {
                      "id": 1,
                      "firstName": "firstName",
                      "lastName": "lastName",
                      "age": 11
                    }
                """

        when:
        def jsonResponse = mockMvc.perform(
                get(url).contentType(APPLICATION_JSON)
        ).andReturn()

        then:
        1 * studentService.getById(id) >> response
        jsonResponse.response.status == OK.value()
        JSONAssert.assertEquals(expectedResponse.toString(), jsonResponse.response.contentAsString.toString(), true)
    }

    def "TestRetrieveAll success case"() {
        given:
        def url = "/v1/students"
        def pageNumber = 0
        def pageSize = 10
        def pageRequest = CustomPageRequest.of(pageNumber, pageSize)
        def response = StudentResponse.builder()
                .id(1L)
                .firstName("firstName")
                .lastName("lastName")
                .age(20)
                .build()
        def pageableResponse = PageableStudentResponse.builder()
                .students([response])
                .totalPages(1)
                .totalElements(1)
                .hasNextPage(false)
                .build()
        def expectedResponse =
                """
                    {
                      "students": [
                        {
                          "id": 1,
                          "firstName": "firstName",
                          "lastName": "lastName",
                          "age": 20
                        }
                      ],
                      "totalElements": 1,
                      "totalPages": 1,
                      "hasNextPage": false
                    }
                """

        when:
        def jsonResponse = mockMvc.perform(
                get(url)
                        .contentType(APPLICATION_JSON)
                        .param("page", pageNumber as String)
                        .param("size", pageSize as String)
        ).andReturn()

        then:
        1 * studentService.getAll(pageRequest) >> pageableResponse
        jsonResponse.response.status == OK.value()
        JSONAssert.assertEquals(expectedResponse.toString(), jsonResponse.response.contentAsString.toString(), true)
    }

    def "TestUpdateById success case"() {
        given:
        def id = 1L
        def url = "/v1/students/$id"
        def request = StudentRequest.builder()
                .firstName("firstName")
                .lastName("lastName")
                .age(11)
                .build()
        def jsonRequest =
                """
                    {
                      "firstName": "firstName",
                      "lastName": "lastName",
                      "age": 11
                    }
                """

        when:
        mockMvc.perform(
                put(url)
                        .content(jsonRequest)
                        .contentType(APPLICATION_JSON)
        ).andReturn()

        then:
        1 * studentService.updateById(id, request)
    }

    def "TestDeleteById success case"() {
        given:
        def id = 1L
        def url = "/v1/students/$id"

        when:
        mockMvc.perform(
                delete(url).contentType(APPLICATION_JSON)
        ).andReturn()

        then:
        1 * studentService.deleteById(id)
    }
}