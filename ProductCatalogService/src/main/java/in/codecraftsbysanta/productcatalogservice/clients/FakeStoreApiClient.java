package in.codecraftsbysanta.productcatalogservice.clients;

import in.codecraftsbysanta.productcatalogservice.dtos.FakeStoreProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class FakeStoreApiClient {
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;
    public FakeStoreProductDTO getProductById(Long productId) {
        ResponseEntity<FakeStoreProductDTO> fakeStoreProductDtoResponseEntity =
                requestForEntity("http://fakestoreapi.com/products/{productId}",HttpMethod.GET,null,
                        FakeStoreProductDTO.class,productId);
        return validateFakeStoreResponse(fakeStoreProductDtoResponseEntity);
    }
    private <T> ResponseEntity<T> requestForEntity(String url, HttpMethod httpMethod, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }
    private FakeStoreProductDTO validateFakeStoreResponse(ResponseEntity<FakeStoreProductDTO> fakeStoreProductDtoResponseEntity) {
        if(fakeStoreProductDtoResponseEntity.getStatusCode().equals(HttpStatusCode.valueOf(200))
                && fakeStoreProductDtoResponseEntity.getBody() != null) {
            return fakeStoreProductDtoResponseEntity.getBody();
        }
        return null;
    }
}