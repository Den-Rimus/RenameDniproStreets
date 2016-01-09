package ua.dp.rename.dniprostreets.api;

import retrofit.http.GET;
import rx.Observable;
import ua.dp.rename.dniprostreets.entity.ApiDataHolder;
import ua.dp.rename.dniprostreets.entity.LastUpdateHolder;

public interface RenamedObjectsService {

    @GET("/rename.json")
    Observable<ApiDataHolder> getJson();

    @GET("/last_data_update.json")
    Observable<LastUpdateHolder> getLastDataUpdateTimestamp();
}
