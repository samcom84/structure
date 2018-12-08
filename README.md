# structure

add permission to manifest.xml

    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    
Step 1. Add the JitPack repository to your build file

	allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}
  
Step 2. Add the dependency in app level build.gradle
	dependencies {
	        implementation 'com.github.samcom84:structure:1.01'
	}
  
Step 3. Apply plugin in Module(app)
    	apply plugin: 'realm-android'

Step 4. add dependency in project level build.gradle
   	classpath "io.realm:realm-gradle-plugin:5.3.1"  (can be latest version)
   
Step 5. create your Application class

	public class ApplicationApp extends App {

	    private static final String DATABASE_NAME = "db_name.realm"; //database name
	    private static final int DATABASE_VERSION = 1;// datbase version
	    private static ApplicationApp _intance = null;

	    public ApplicationApp() {
		_intance = this;
	    }

	    public static Context getContext() {
		return _intance;
	    }

	    public static synchronized ApplicationApp getInstance() {
		return _intance;
	    }

	    @Override
	    public void onCreate() {
		super.onCreate();

		RealmConfiguration mRealmConfiguration = new RealmConfiguration.Builder()
			.name(DATABASE_NAME)
			.schemaVersion(DATABASE_VERSION)
			.modules(new AppModule(),Realm.getDefaultModule())  //AppModule is a RealmModule class. You have to create it in your project
			.deleteRealmIfMigrationNeeded()
			.build();

		Realm.setDefaultConfiguration(mRealmConfiguration);
		Realm.getInstance(mRealmConfiguration);

		//if you are implementing api's then set your base url here 
		ServiceHelper.setBaseUrl("BASE_URL");
	    }
	}

Step 6. create a RealmModule class in your project
  
	 @RealmModule(library = true, allClasses = true)
	public class AppModule extends MyLibraryModule {

	}

it will extends with MyLibraryModule and body will be blank 

Now steps for any api calling 

Step 1. create an ServiceInterface interface for retrofit api methods 

	public interface ServiceInterface {
	    @POST("API METHOD")
	    Call<ServiceResponse> methodName(@Body JsonObject jsonObject);
	}


Step 2. create RealmObject class which store data 
	
	public class CallsInfo extends RealmObject {
	    @PrimaryKey
	    private String id;
	    private String name;
	    //declare all variable here 
	    //variable key should same as response keys.
	    //there should be a primary key in object
	    //implement getter and setter
	}	
  
Step 3. create a apiList class for particular info class 
for example :-  CallsList for CallsInfo

	public class CallsList extends BaseList<CallsInfo> {
	    private static volatile CallsList _instance = null;
	    private ServiceInterface mInterface;
	    private ModelDelegates.ModelDelegate<CallsInfo> m_delegate = null;
	    private Realm realm;

    private CallsList() {
        super(CallsInfo.class);
    }

    public static CallsList Instance() {
        if (_instance == null) {
            synchronized (CallsList.class) {
                _instance = new CallsList();
            }
        }
        return _instance;
    }

    public ServiceInterface getInterface(){
        try{
            mInterface = ServiceHelper.getService().create(ServiceInterface.class);
        }catch (Exception ex) {

        }
        return mInterface;
    }

    public void methodName(final ModelDelegates.ModelDelegate<CallsInfo> m_delegate) {
        JSONObject jsonObjectMain = new JSONObject();
        try {
            jsonObjectMain.put("parameter_key", "data");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObject gsonObject = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        gsonObject = (JsonObject) jsonParser.parse(String.valueOf(jsonObjectMain));

        if (NetworkConnectivity.isConnected()) { //apiMethodNameFromServiceInterface this will be method of api which declared in service interface class
            Call<ServiceResponse> call = getInterface().apiMethodNameFromServiceInterface(gsonObject);
            call.enqueue(new Callback<ServiceResponse>() {

                @Override
                public void onResponse(Call<ServiceResponse> call, Response<ServiceResponse> response) {
                    m_modelList = new ArrayList<>();
                    try {
                        ServiceResponse response1 = response.body();
                        if (!response1.is_error) {
                            if (response1.RawResponse.toString() != null) {
                                String json = new Gson().toJson(response1.RawResponse);
                                JSONArray arr = new JSONArray(json);

                                if (arr != null && arr.length() > 0) {
                                    realm = Realm.getDefaultInstance();
                                    realm.beginTransaction();
                                    realm.createOrUpdateAllFromJson(CallsInfo.class, arr);
                                    realm.commitTransaction();
                                    realm.close();
                                    loadFromDB();
                                    if (m_delegate != null)
                                        m_delegate.ModelLoaded(m_modelList);
                                } else {
                                    if (m_delegate != null)
                                        m_delegate.ModelLoadFailedWithError(NO_DATA_FOUND);
                                }
                            } else {
                                if (m_delegate != null)
                                    m_delegate.ModelLoadFailedWithError(response1.Message);
                            }
                        } else {
                            if (m_delegate != null)
                                m_delegate.ModelLoadFailedWithError(response1.Message);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (m_delegate != null)
                            m_delegate.ModelLoadFailedWithError("" + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ServiceResponse> call, Throwable t) {
                    if (m_delegate != null)
                        m_delegate.ModelLoadFailedWithError(t.getMessage());
                }
            });
        } else {
            if (m_delegate != null)
                m_delegate.ModelLoadFailedWithError(INTERNET_CONNECTION);
        }
    }

	}


How to call api in MainActivity.class

	public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        getList();
    }

    private void getList() {
        showProgress();
        CallsList.Instance().getTodaysCalls(new ModelDelegates.ModelDelegate<CallsInfo>() {
            @Override
            public void ModelLoaded(ArrayList<CallsInfo> list) {
                hideProgress();
                //ArrayList<CallsInfo> mList =  BaseModel.Instance().getAll(CallsInfo.class);

                Toast.makeText(MainActivity.this, "Success :- " + list.size(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void ModelLoadFailedWithError(String error) {
                hideProgress();
                Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }
	}




