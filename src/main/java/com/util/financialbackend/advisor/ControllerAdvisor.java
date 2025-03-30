package com.util.financialbackend.advisor;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerAdvisor {
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Error> advise(RuntimeException e){
        log.info(String.format("exception:%s",e));
        return ResponseEntity.status(500).body(new Error(e.getMessage()));
    }
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public void notAdvise(){
        //do nothing
    }
}
