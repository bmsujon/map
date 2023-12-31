package com.jachai.map.util;


import com.jachai.map.dto.rest.request.GenericRequest;
import com.jachai.map.dto.rest.response.SimpleMessageResponseREST;
import com.jachai.map.exception.InternalIOException;
import com.jachai.map.exception.Non200Exception;
import com.jachai.map.exception.unavoidable.CouldNotConnect;
import com.jachai.map.exception.unavoidable.KnownFailureInRemote;
import com.jachai.map.exception.unavoidable.UnknownResultFromRemote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;


@Slf4j
public class InternalRESTProvider {

    public static SimpleMessageResponseREST doRestSafe(HttpMethod httpMethod, URI url, GenericRequest request, Class responseType, HttpHeaders headers)
            throws CouldNotConnect, UnknownResultFromRemote, KnownFailureInRemote {
        try {
            return doRest(httpMethod, url, request, responseType, headers);
        } catch (ResourceAccessException rae) {
            throw new CouldNotConnect("Error connecting to " + url.getHost(), rae);
        } catch (InternalIOException i) {
            throw new UnknownResultFromRemote("Who knows what happened in + " + url.getHost(), null, i);
        } catch (Non200Exception n2e) {
            if (n2e.statusCode == HttpStatus.REQUEST_TIMEOUT.value())
                throw new UnknownResultFromRemote("Timeout in + " + url.getHost(), null, n2e);
            else
                throw new KnownFailureInRemote(n2e.getMessage(), null, n2e);
        }
    }

    public static SimpleMessageResponseREST doRestSafe(HttpMethod httpMethod, URI url, GenericRequest request, Class responseType)
            throws CouldNotConnect, UnknownResultFromRemote, KnownFailureInRemote {
        return doRestSafe(httpMethod, url, request, responseType, new HttpHeaders());
    }

    public static SimpleMessageResponseREST doRest(HttpMethod httpMethod, URI url, GenericRequest request, Class responseType) {
        return doRest(httpMethod, url, request, responseType, new HttpHeaders());
    }

    public static SimpleMessageResponseREST doRestForm(HttpMethod httpMethod, URI url,HttpHeaders headers, MultiValueMap requestMultiValueMap, Class responseType) {

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(requestMultiValueMap, headers);
        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        restTemplate.setErrorHandler(new JachaiResponseErrorHandler());
        try{
            ResponseEntity<? extends SimpleMessageResponseREST> responseREST = restTemplate.exchange(url, httpMethod, request, responseType);

            log.info("Request form: {}", responseREST.toString());
            return responseREST.getBody();
        } catch (RestClientResponseException e) {
            log.debug("Got {} {}", e.getRawStatusCode(), e.getResponseBodyAsString());

            throw RestUtils.generateAppropriateException(e);

        }

    }

    public static SimpleMessageResponseREST doRest(HttpMethod httpMethod, URI url, GenericRequest request, Class responseType, HttpHeaders headers) {

        log.info("Remote {} : {}", httpMethod.name(), url);
        if (request != null)
            log.info("Request object: {}", request);

        if ( headers.getContentType() == null )
            headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<GenericRequest> requestHttpEntity = new HttpEntity<>(request, headers);

        if (responseType == null)
            throw new IllegalArgumentException("ResponseType in internalRestProvider cannot be null");

        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        restTemplate.setErrorHandler(new JachaiResponseErrorHandler());

        try {

            ResponseEntity<? extends SimpleMessageResponseREST> responseREST = restTemplate.exchange(url, httpMethod, requestHttpEntity, responseType);

            log.info("Got {} {}", responseREST.getStatusCodeValue(), responseREST.getBody());
            return responseREST.getBody();
        } catch (RestClientResponseException e) {
            log.debug("Gote {} {}", e.getRawStatusCode(), e.getResponseBodyAsString());

            throw RestUtils.generateAppropriateException(e);

        }
    }


}
