/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (4.3.1).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package club.api;

import java.time.LocalDate;
import club.api.PaymentOutput;
import java.util.UUID;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-12-06T16:13:48.874635400+10:00[Asia/Vladivostok]")

@Validated
@Api(value = "payment", description = "the payment API")
public interface PaymentApi {

    /**
     * PUT /payment/{date}
     *
     * @param date DATE (required)
     * @return OK (status code 200)
     */
    @ApiOperation(value = "", nickname = "paymentDatePut", notes = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK") })
    @RequestMapping(value = "/payment/{date}",
        method = RequestMethod.PUT)
    ResponseEntity<Void> paymentDatePut(@Pattern(regexp="^\\d{2}.\\d{2}.\\d{4}$") @ApiParam(value = "DATE",required=true) @PathVariable("date") LocalDate date);


    /**
     * GET /payment
     *
     * @return OK (status code 200)
     */
    @ApiOperation(value = "", nickname = "paymentGet", notes = "", response = PaymentOutput.class, tags={ "payment-controller", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = PaymentOutput.class) })
    @RequestMapping(value = "/payment",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<PaymentOutput> paymentGet();


    /**
     * PUT /payment/{int}
     *
     * @param _int INTEGER (required)
     * @return OK (status code 200)
     */
    @ApiOperation(value = "", nickname = "paymentIntPut", notes = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK") })
    @RequestMapping(value = "/payment/{int}",
        method = RequestMethod.PUT)
    ResponseEntity<Void> paymentIntPut(@ApiParam(value = "INTEGER",required=true) @PathVariable("int") Integer _int);


    /**
     * PUT /payment/{uuid}
     *
     * @param uuid UUID (required)
     * @return OK (status code 200)
     */
    @ApiOperation(value = "", nickname = "paymentUuidPut", notes = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK") })
    @RequestMapping(value = "/payment/{uuid}",
        method = RequestMethod.PUT)
    ResponseEntity<Void> paymentUuidPut(@ApiParam(value = "UUID",required=true) @PathVariable("uuid") UUID uuid);

}