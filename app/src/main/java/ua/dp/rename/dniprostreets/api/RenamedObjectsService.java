package ua.dp.rename.dniprostreets.api;

import retrofit.http.GET;
import rx.Observable;
import ua.dp.rename.dniprostreets.entity.ApiDataHolder;

public interface RenamedObjectsService {

    @GET("/rename.json")
    Observable<ApiDataHolder> getJson();
}
