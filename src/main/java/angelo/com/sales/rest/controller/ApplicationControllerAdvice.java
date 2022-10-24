package angelo.com.sales.rest.controller;

import angelo.com.sales.exception.BusinessException;
import angelo.com.sales.exception.OrderNotFoundException;
import angelo.com.sales.rest.ApiErrors;
import static org.springframework.http.HttpStatus.*;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ApplicationControllerAdvice {

   @ExceptionHandler(BusinessException.class)
   @ResponseStatus(BAD_REQUEST)
   public ApiErrors handleBusinessException (BusinessException e, HttpServletRequest request) {
      return new ApiErrors(System.currentTimeMillis(), e.getMessage(), request.getRequestURI());
   }

   @ExceptionHandler(OrderNotFoundException.class)
   @ResponseStatus(NOT_FOUND)
   public ApiErrors handleOrderNotFoundException (OrderNotFoundException e, HttpServletRequest request) {
      return new ApiErrors(System.currentTimeMillis(), e.getMessage(), request.getRequestURI());
   }

   @ExceptionHandler(MethodArgumentNotValidException.class)
   @ResponseStatus(BAD_REQUEST)
   public ApiErrors handleMethodNotValidException(MethodArgumentNotValidException e, HttpServletRequest request){
      return new ApiErrors(System.currentTimeMillis(),
                          e.getBindingResult().getAllErrors().stream().map(err -> err.getDefaultMessage()).toList(),
                          request.getRequestURI());
   }
}
