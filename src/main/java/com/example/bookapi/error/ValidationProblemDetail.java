package com.example.bookapi.error;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;

import java.util.List;
import java.util.Map;

@Data
@RequiredArgsConstructor
public class ValidationProblemDetail extends ProblemDetail {

    private Map<String, List<String>> errors;

}
