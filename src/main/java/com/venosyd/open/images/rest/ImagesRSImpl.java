package com.venosyd.open.images.rest;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import com.venosyd.open.images.logic.ImagesBS;
import com.venosyd.open.commons.util.JSONUtil;
import com.venosyd.open.commons.util.RESTService;

/**
 * @author sergio lisan <sels@venosyd.com>
 */
@Path("/")
public class ImagesRSImpl implements ImagesRS, RESTService {

    @Context
    private HttpHeaders headers;

    @Override
    public Response echo() {
        String message = "IMAGE ECHO GRANTED" + Calendar.getInstance().get(Calendar.YEAR);

        var echoMessage = new HashMap<String, String>();
        echoMessage.put("status", "ok");
        echoMessage.put("message", message);

        return makeResponse(echoMessage);
    }

    @Override
    public Response save(String body) {
        Function<Map<String, String>, Response> operation = (request) -> {
            var collection = request.get("collection");
            var item = request.get("item");

            // heavy workload
            var file = request.get("file");

            byte[] data;
            if (file.contains(","))
                data = Base64.getDecoder().decode(file.substring(file.indexOf(",") + 1));
            else
                data = Base64.getDecoder().decode(file);

            var returned = ImagesBS.INSTANCE.save(collection, item, new ByteArrayInputStream(data));

            var result = new HashMap<String, String>();

            if (returned != null) {
                result.put("status", "ok");
                result.put("message", "SAVE IMAGE SUCCESS");
                result.put("payload", JSONUtil.toJSON(returned));

                return makeResponse(result);
            } else {
                return makeErrorResponse("SAVE IMAGE FAILURE");
            }
        };

        String authorization = getauthcode(headers);
        var arguments = Arrays.<String>asList("database", "collection", "id");

        return authorization != null ? process(_unwrap(body, true), authorization, arguments, operation) // headers
                : process(_unwrap(body, false), operation); // post token
    }

    @Override
    public Response load(String body) {
        Function<Map<String, String>, Response> operation = (request) -> {
            var collection = request.get("collection");
            var item = request.get("item");

            var returned = ImagesBS.INSTANCE.load(collection, item);

            var result = new HashMap<String, String>();

            if (returned != null) {
                result.put("status", "ok");
                result.put("message", "LOAD IMAGE SUCCESS");
                result.put("payload", returned);

                return makeResponse(result);
            } else {
                return makeErrorResponse("LOAD IMAGE FAILURE");
            }
        };

        var authorization = getauthcode(headers);
        var arguments = Arrays.<String>asList("database", "collection", "id");

        return authorization != null ? process(_unwrap(body, true), authorization, arguments, operation) // headers
                : process(_unwrap(body, false), operation); // post token
    }

    //
    // PRIVATE METHODS
    //

    private Map<String, String> _unwrap(String body, boolean withouttoken) {
        body = unzip(body);
        var request = JSONUtil.<String, String>fromJSONToMap(body);

        if (request.containsKey("hash")) {
            var hash = request.get("hash");

            String token;
            String database;

            if (withouttoken) {
                database = hash.substring(0);
            } else {
                token = hash.substring(0, 64);
                database = hash.substring(64);

                request.put("token", token);

            }

            request.put("database", database);
        }

        return request;
    }

}
