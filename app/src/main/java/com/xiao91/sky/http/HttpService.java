package com.xiao91.sky.http;

import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * 封装所有retrofit2访问所需的interface接口
 *
 * 主机地址设置位置，{@link HttpManager HOST}，URL地址和参数key统一在这里写出
 *
 * @date 2017.03.10
 *
 */

public interface HttpService {

    /**
     * 上传文件和描述
     *
     * @param description 文件描述信息
     * @param img         图片
     * @return
     */
    @Multipart
    @POST("上传的地址")
    Observable<String> uploadFile(
            @Part("fileName") RequestBody description,
            @Part("file\"; filename=\"image.png\"") RequestBody img
    );


    /**
     * 上传图片和图片描述，图片类型，用户id等其它信息
     * 如果除了图片外还需要传递其它信息，只需要增加几个@Part就可以了。
     *
     * @param describe 图片描述
     * @param type  图片类型
     * @param userid    用户id
     * @param description 文件描述的RequestBody实例
     * @param img   RequestBody 文件实例
     * @return
     */
    @Multipart
    @POST("上传的地址")
    Observable<String> uploadUserFileAndId(
            @Part("describe") String describe,
            @Part("type") String type,
            @Part("userid") String userid,
            @Part("fileName") RequestBody description,
            @Part("file\"; filename=\"image.png\"") RequestBody img
    );
}
