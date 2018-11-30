package com.coffice.shengtao.cofficemachine.httprequest;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServiceManger<T> {
    private static final int DEFAULT_TIME_OUT=5;//超时时间
    private static final int DEFAULT_READ_OUT=10;//读超时时间
    //静态块,获取OkHttpClient对象
    static {
        getOkHttpClient();
    }
    public static OkHttpClient okHttpClient;
    private String baseUrl;
    private Retrofit mRetrofit=null;
    private static RetrofitServiceManger mRetrofitServiceManager=null;

    private RetrofitServiceManger(String baseurl) {
        this.baseUrl=baseurl;
        initRetrofit();
    }
    public static synchronized  RetrofitServiceManger getInstance(String baseurl){
        if(mRetrofitServiceManager==null) {
            synchronized (RetrofitServiceManger.class) {
                mRetrofitServiceManager= new RetrofitServiceManger(baseurl);
            }
        }
        return mRetrofitServiceManager;
    }
    //单例模式获取okhttp
    public static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            synchronized (OkHttpClient.class) {
                if (okHttpClient == null) {
                    okHttpClient = new OkHttpClient.Builder()
                            //打印拦截器日志
                            .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                            .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)//设置连接超时时间
                            .readTimeout(DEFAULT_READ_OUT, TimeUnit.SECONDS)//设置读取超时时间
                            .writeTimeout(DEFAULT_READ_OUT, TimeUnit.SECONDS)//设置写入超时时间
                            .build();
//                    连接超时时间，实际项目中，我们可能有一些公共的参数，如 ，设备信息，渠道，Token 之类的，每个接口都需要用，我们可以写一个拦截器，然后配置到OKHttpClient里，通过 builder.addInterceptor(basicParamsInterceptor) 添加，这样我们就不用每个接口都添加这些参数了。
//                    BasicParamsInterceptor basicParamsInterceptor = new BasicParamsInterceptor.Builder()
//                            .addHeaderParam("userName", "")//添加公共参数
//                            .addHeaderParam("device", "")
//                            .build();
//                    builder.addInterceptor(basicParamsInterceptor);

                }
            }
        }
        return okHttpClient;
    }

    private void initRetrofit() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    /**用于生成接口实例*/

    public T create(Class service){
        return (T) mRetrofit.create(service);
    }
}


   /* Retrofit 使用  ----
    1.建立接口 返回Call
    2.创建Retrofit 实体 加入Url baseUrl("https://api.douban.com/v2/").addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
    3.创建接口实体对象      RetrofitService service = retrofit.create(RetrofitService.class);
    4.对象调用方法 --call   Call<Book> call =  service.getSearchBook("金瓶梅", null, 0, 1);
    5.call执行在线程        call.enqueue(new Callback<Book>()
    OkHttp结合
        okHttpClient = new OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .build();
        Request request = new Request.Builder()
        .url(url)
        .get()
        .build();
        okHttpClient.newCall(request).enqueue(new Callback()
    Retrofit 创建实体的时候 .client(okhttp) 设置进去
    Rx结合
    1.在创建Retorfit时 .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//支持RxJava
    2.Servers 返回的是observable对象
    3.observable  运行在线程中
    observable.subscribeOn(Schedulers.io())//请求数据的事件发生在io线程
     .observeOn(AndroidSchedulers.mainThread())//请求完成后在主线程更显UI
     .subscribe(new Observer<Book>()

    Retrofit：Retrofit是Square公司开发的一款针对Android 网络请求的框架（底层默认是基于OkHttp 实现）。
    OkHttp：也是Square公司的一款开源的网络请求库。
    RxJava ："a library for composing asynchronous and event-based programs using observable sequences for the Java VM"（一个在 Java VM 上使用可观测的序列来组成异步的、基于事件的程序的库）。RxJava使异步操作变得非常简单。
    各自职责：Retrofit 负责 请求的数据 和 请求的结果，使用 接口的方式 呈现，OkHttp 负责请求的过程，RxJava 负责异步，各种线程之间的切换。


第一，将我们需要注入的对象的类的构造参数使用@Inject标注，告诉dagger2它可以实例化这个类；
第二，编写Component接口使用@Component进行标注，里面的void inject()的参数表示要将依赖注入到的目标位置；
第三，使用android studio的Build菜单编译一下项目，使它自动生成我们编写的Component所对应的类，生成的类的名字的格式为 "Dagger+我们所定义的Component的名字"；
第四，在需要注入的类中使用@Inject标注要注入的变量；然后调用自动生成的Component类的方法create()或builder().build()，然后inject到当前类；在这之后就可以使用
这个@Inject标注的变量了。
*/