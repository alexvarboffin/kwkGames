package com.walhalla.luckypunch.activity;

import static org.apache.mvp.presenter.MainPresenter.NONENONE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.ExtendedWebView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.Toolbar;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.lib.navigation.Navigation;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import com.walhalla.luckypunch.R;
import com.walhalla.luckypunch.fragment.BaseFragment;
import com.walhalla.ui.DLog;
import com.walhalla.ui.Module_U;

import org.apache.P;
import org.apache.Utils;
import org.apache.cordova.ChromeView;
import org.apache.cordova.GConfig;
import org.apache.cordova.ScreenType;
import org.apache.cordova.domen.BodyClass;
import org.apache.cordova.domen.Dataset;
import org.apache.cordova.enumer.UrlSaver;
import org.apache.cordova.fragment.WebFragment;
import org.apache.cordova.repository.AbstractDatasetRepository;
import org.apache.cordova.repository.impl.FirebaseRepository;

import org.apache.mvp.MainView;
import org.apache.mvp.presenter.MainPresenter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public abstract class BaseAbstractActivity extends AppCompatActivity
        implements org.apache.cordova.fragment.WebFragment.Lecallback,
        SwipeRefreshLayout.OnRefreshListener,
        org.apache.mvp.MainView, BaseFragment.IOnFragmentInteractionListener
{

    //private boolean doubleBackToExitPressedOnce = true;
    protected GConfig aaa;

    //Views//@@@ addFragment(R.id.container, F_PagerContainer.newInstance("1", "2"));

    //public SwipeRefreshLayout swipeRefreshLayout;
    //protected ExtendedWebView __mView;
    //protected RelativeLayout main;


    private boolean rotated0 = false;

    protected MainPresenter presenter;
    private Navigation navigation;

    protected Toolbar toolbar;

    //protected ViewTreeObserver.OnScrollChangedListener mOnScrollChangedListener;
    private Thread mThread;
    private Handler mHandler;
    private NavigationView navigationView;
    //protected boolean isUnlocked = true;
    //private TPreferences pref;


    @Override
    public void hiDeRefreshLayout() {
//        if (swipeRefreshLayout.isRefreshing()) {
//            swipeRefreshLayout.setRefreshing(false);
//        }
    }

    private static final int PURCHASE_REQUEST_CODE = 1001;


    //private static boolean unlocked = true; //Unlocked Offer www btn
    private AlertDialog dialog;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (PURCHASE_REQUEST_CODE == requestCode) {
            //MyTracker.onActivityResult(resultCode, data);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //this.pref = TPreferences.getInstance(this);

        mHandler = new Handler(Looper.getMainLooper());
        //mHandler = new Handler();

        aaa = GCfc();
        navigation = new Navigation(this);
        presenter = new MainPresenter(this, this, aaa, mHandler, makeTracker());

        //main = findViewById(R.id.main);
        //pb = findViewById(R.id.progressBar1);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("@!");

        if (aaa.TOOLBAR_ENABLED) {
//            toolbar.setSubtitle(Util.getAppVersion(this));
//            toolbar.setVisibility(View.VISIBLE);
        }
        //drawer();
        DLog.d("ON_CREATE: " + this.getClass().getSimpleName() + (toolbar == null));
        if (!rotated()) {
            presenter.init0(this);
        }
        //Generate Dynamic Gui
        //onViewCreated(clazz1, this);

//        Uri targetUrl =
//                AppLinks.getTargetUrlFromInboundIntent(this, getIntent());
//        if (targetUrl != null) {
//            Log.i(TAG, "App Link Target URL: " + targetUrl.toString());
//        }

//                if (rawUrl != null) {
//
//                    HttpPostRequest getRequest = new HttpPostRequest();
//                    try {
//                        String device_id = Util.phoneId(BaseActivity.this.getApplicationContext());
//
//                        //Replace deep to tracker
//                        URI uri = new URI(getString(R.string.app_url));
//
//                        String tracker =
//                                rawUrl.replace(getString(R.string.app_scheme), uri.getScheme())
//                                        .replace(getString(R.string.app_host), uri.getHost())
//                                        .replace("%26", "&")
//                                        .replace("%3D", "=")
//                                        + "&id=" + device_id;
//
////                        if (BuildConfig.DEBUG) {
////                            Log.i(TAG, "URL: " + rawUrl);
////                            Log.i(TAG, "TR: " + uri.getScheme() + "|" + uri.getHost());
////                            Log.i(TAG, "--->: " + tracker);
////
////                            //Only for Google Chrome test
////                            //
////
////                            Log.i(TAG, "CHROME: "
////                                    + ("intent://"
////                                    + getString(R.string.app_host)
////                                    + "/#Intent;scheme="
////                                    + getString(R.string.app_scheme) + ";package=" + getPackageName())
////                                    //        .replace(";",";")
////                                    + ";end"
////                            );
////                        }
//
//                        LocalStorage storage = LocalStorage.getInstance(BaseActivity.this);
//                        JSONObject parent = new JSONObject();
//                        try {
//                            parent.put("dl", rawUrl);
//                            parent.put("ref", storage.referer());
//                            getRequest.execute(parent).get();
//                        } catch (JSONException e) {
//                            DLog.handleException(e);
//                        }
//                    } catch (ExecutionException e) {
//                        DLog.handleException(e);
//                    } catch (InterruptedException e) {
//                        DLog.handleException(e);
//                    } catch (URISyntaxException e) {
//                        DLog.handleException(e);
//                    }
//                }

//        String device_id = Util.phoneId(MainActivity.getAppContext().getApplicationContext());
//        launch(webview_url + "?id=" + device_id);

//###        if (savedInstanceState != null) {
//###            return;
//###        }

        if (checkUpdate()) {
//            if (toolbar != null) {
//                toolbar.post(() -> Module_U.checkUpdate(this));
//            }
        }


//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(view ->
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).
//                        setAction("Action", null).
//
//                        show());

//        loan = new ViewModelProvider(this).get(LoanViewModel.class);
//        loan.getLoan().observe(this, new Observer<Loan>() {
//            @Override
//            public void onChanged(Loan s) {
//                DLog.d("#####################-->" + loan.toString());
//            }
//        });

//        loadUrl(this.launchUrl);


        // Obtain the FirebaseAnalytics instance.
        //FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        navigation = new Navigation(this);

        /**
         * PagerContainerFragment
         * CategoryListFragment
         * CategoryListFragment
         * CategoryListFragment
         */
        //Home Screen
        //addFragment(R.id.container, CategoryListFragment.newInstance(0, null));

        //addFragment(R.id.container, SimpleListFragment.newInstance());

//        throw new RuntimeException("wtf");
//        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
//        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
//        filter.addAction(MyFirebaseJobService.ACTION_SHOW_MESSAGE);
//        registerReceiver(br, filter);

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "myapp:htracker");
        wakeLock.acquire();

        //MyTracker.getTrackerConfig().setTrackingEnvironmentEnabled(true);
        //handleDepplink(getIntent());

        //Module_U.anomaly(this);

//        if (findViewById(R.id.container) != null) {
//            if (savedInstanceState != null) {
//                return;
//            }
//
//            //addFragment(R.id.container, CategoryListFragment.newInstance());
//            //getSupportFragmentManager().beginTransaction().add(R.id.container, new ScreenRSSList()).commit();
////            MenuItem item = navigationView.getMenu().getItem(0);
////            if (item.hasSubMenu()) {
////                onNavigationItemSelected(item.getSubMenu().getItem(0));
////            } else {
////                onNavigationItemSelected(item);
////            }
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.container, F_PagerContainer.newInstance())
//                    .commit();
//        }
    }

//    private void loadHomeFragment(String currentTag, boolean clearStack) {
//        DLog.d("--> " + currentTag);
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        // selecting appropriate nav menu item
//        //@@@ selectNavMenu();
//
//        // set toolbar title
//        //setToolbarTitle();
//
//        // if user select the current navigation menu again, don't do anything
//        // just close the navigation drawer
//        Fragment aa = getSupportFragmentManager().findFragmentByTag(currentTag);
//        if (aa != null && aa.isVisible()) {
//            drawer.closeDrawers();
//
//            // show or hide the fab button
//            //@@@ toggleFab();
//            return;
//        }
//
//        // show or hide the fab button
//        //@@@ toggleFab();
//        //Closing drawer on item click
//        drawer.closeDrawers();
//
//        // refresh toolbar menu
//        invalidateOptionsMenu();
//
//        // Sometimes, when fragment has huge data, screen seems hanging
//        // when switching between navigation menus
//        // So using runnable, the fragment is loaded with cross fade effect
//        // This effect can be seen in GMail app
////        Runnable mPendingRunnable = new Runnable() {
////            @Override
////            public void run() {
////
////                try {
////                    TimeUnit.MILLISECONDS.sleep(7000);
////                    // update the main content by replacing fragments
////                    Fragment fragment = getHomeFragment();
////                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
////                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
////                            android.R.anim.fade_out);
////                    fragmentTransaction.replace(R.id.frame_container, fragment, CURRENT_TAG);
////                    fragmentTransaction.commitAllowingStateLoss();
////
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
////            }
////        };
////
////        // If mPendingRunnable is not null, then add to the message queue
////        if (mPendingRunnable != null) {
////            mHandler.post(mPendingRunnable);
////        }
//
//        mThread = new Thread(() -> {
//            try {
//                TimeUnit.MILLISECONDS.sleep(400);
//                mHandler.post(() -> {
//
//                    //Clear back stack
//                    //final int count = getSupportFragmentManager().getBackStackEntryCount();
//                    if (clearStack) {
//                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                    }
//
//                    // update the main content by replacing fragments
//                    Fragment fragment = NavHelper.getHomeFragment(currentTag);
//                    FragmentTransaction fr = getSupportFragmentManager().beginTransaction();
//                    fr.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
//          //@@@          fr.replace(R.id.container, fragment, CURRENT_TAG);
//
//
////                    fr.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
////                    fr.replace(R.id.container, fragment);
////                    fr.addToBackStack(null);
//                    //fr._NOT_USE_commit();
//                    fr.commitAllowingStateLoss();
//                });
//                //mThread.interrupt();
//            } catch (InterruptedException e) {
////                Toast.makeText(this, e.getLocalizedMessage()
////                        + " - " + mThread.getName(), Toast.LENGTH_SHORT).show();
//            }
//        }, "my-threader");
//
//
//        if (!mThread.isAlive()) {
//            try {
//                mThread.start();
//            } catch (Exception r) {
//                DLog.handleException(r);
//            }
//        }
//    }

//    protected void onViewCreated(ViewGroup view, Context context) {
//        //mWebView = privacy.findViewById(R.id.web_view);
//        //swipeRefreshLayout = privacy.findViewById(R.id.refresh);
//        swipeRefreshLayout = new SwipeRefreshLayout(context);
//        __mView = new ExtendedWebView(new ContextThemeWrapper(context, R.style.AppTheme));
//        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        swipeRefreshLayout.setLayoutParams(lp);
//        __mView.setLayoutParams(lp);
//        view.addView(swipeRefreshLayout);
//        swipeRefreshLayout.addView(__mView);
//        swipeRefreshLayout.getViewTreeObserver().addOnScrollChangedListener(mOnScrollChangedListener = () -> {
//            swipeRefreshLayout.setEnabled(__mView.getScrollY() == 0);
//        });
//
//        swipeRefreshLayout.setOnRefreshListener(this);
//
//        //mWebView.setVisibility(View.INVISIBLE);
//        //webview.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
//
//        Utils.a123(new ChromeView() {
//
//            @Override
//            public void webClientError(int errorCode, String description, String failingUrl) {
//                makeScreen(new Dataset(ScreenType.WEB_VIEW, NONENONE));
//                if (toolbar != null) {
//                    toolbar.setSubtitle("");
//                }
//            }
//
//            @Override
//            public void mAcceptPressed(String url) {
//            }
//
//            @Override
//            public void eventRequest(BodyClass bodyClass) {
//                presenter.event(bodyClass);
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                String title = view.getTitle();
//                if (webTitle() && !TextUtils.isEmpty(title) && toolbar != null) {
//                    if (view.getUrl() != null && title.startsWith(view.getUrl())) {
//                        toolbar.setSubtitle(title);
//                    }
//                }
//                pb.setVisibility(View.GONE);
//
//                //        ShowOrHideWebViewInitialUse = "hide";
//                //        privacy.setVisibility(View.VISIBLE);
//
//                __mView.setVisibility(View.VISIBLE);
//                main = findViewById(R.id.main);
//                if (main != null) {
//                    main.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onPageStarted() {
//                //@@@
//                if (aaa.PROGRESSBAR_ENABLED) {
//                    pb.setVisibility(View.VISIBLE);
//                }
//            }
//        }, __mView);
//    }


    //Fabric.with(this, new Crashlytics()); //crashReport
    //setContentView(R.layout.activity_main);

//    @Override
//    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

//    @SuppressLint("NonConstantResourceId")
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int itemId = item.getItemId();
////        if (itemId == android.R.id.home) {
////            onBackPressed();
////            return true;
////        } else
//
//        if (itemId == R.id.action_about) {
//            Module_U.aboutDialog(this);
//            return true;
//        } else if (itemId == R.id.action_privacy_policy) {
//            Module_U.openBrowser(this, getString(R.string.url_privacy_policy));
//            return true;
//        } else if (itemId == R.id.action_rate_app) {
//            Module_U.rateUs(this);
//            return true;
//        } else if (itemId == R.id.action_share_app) {
//            Module_U.shareThisApp(this);
//            return true;
//        } else if (itemId == R.id.action_privacy_policy_credit) {
//            AboutBox.showPolicy(this);
//            return true;
//        } else if (itemId == R.id.action_discover_more_app) {
//            Module_U.moreApp(this);
//            return true;
////            case R.id.action_exit:
////                this.finish();
////                return true;
//        } else if (itemId == R.id.action_feedback) {
//            Module_U.feedback(this);
//            return true;
//        } else if (itemId == R.id.action_terms) {
//            startActivity(new Intent(this, TermsActivity.class));
//            return true;
//
////            case R.id.action_more_app_01:
////                Module_U.moreApp(this, "com.walhalla.ttloader");
////                return true;
//
////            case R.id.action_more_app_02:
////                Module_U.moreApp(this, "com.walhalla.vibro");
////                return true;
//        }
//        return super.onOptionsItemSelected(item);
//
//        // User didn't trigger a refresh, let the superclass handle this action
//        //return super.onOptionsItemSelected(item);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        switch (item.getItemId()) {
//            case R.id.action_refresh:
//
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

//    private JobBroadcastReceiver br = new JobBroadcastReceiver();
//
//    private class JobBroadcastReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            StringBuilder sb = new StringBuilder();
//            sb.append("Action: " + intent.getAction() + "\n");
//            sb.append("URI: " + intent.toUri(Intent.URI_INTENT_SCHEME).toString() + "\n");
//            String log = sb.toString();
//            Log.d(TAG, log);
//            Toast.makeText(context, log, Toast.LENGTH_LONG).show();
//
//        }
//    }


    @Override
    public Dataset data() {
        return null;// Payload
    }

    /**
     * If true активирует Web
     */
    public void makeScreen(Dataset screen) {
//        isUnlocked = screen.enabled;
//        if (screen != null) {
//            DLog.d("@@@");
//
//            if (BuildConfig.DEBUG) {
//                String info = "{}: " + screen.screenType
//                        + "\t" + screen.getUrl() + " " + isUnlocked;
//                getSupportActionBar().setSubtitle(info);
//            }
//        }

//        if (1 == 1) {
//            replaceFragment(WebFragment.newInstance(
//                    "https://kzmoney.web.app/cookies.html", aaa));
//            return;
//        }


        if (screen.screenType == ScreenType.WEB_VIEW) {
            //Toast.makeText(this, ""+screen.toString(), Toast.LENGTH_LONG).show();
            //clazz1.setVisibility(View.GONE);
            //main.setVisibility(View.VISIBLE);
            //mWebView.setVisibility((web) ? View.VISIBLE : View.GONE);
            //pb.setVisibility(View.GONE);
            externalSetter(screen);
            if (enableHomeWebView()) {
                replaceFragment(WebFragment.newInstance(screen.url, aaa));
            }
        } else {
            //Также сработает при screen==null
            DLog.d("[screen]: " + screen.screenType + "\t" + screen.getUrl());

            if (screen.screenType == ScreenType.GAME_VIEW) {

                if (orientation404() != null && this.getRequestedOrientation() != orientation404()) {
                    this.setRequestedOrientation(orientation404());
                }

            } else if (screen.screenType == ScreenType.WEB_VIEW) {
                if (orientationWeb() != null && this.getRequestedOrientation() != orientationWeb()) {
                    this.setRequestedOrientation(orientationWeb());
                }
                launch(screen.getUrl());
            }
            Utils.hideKeyboard(this);
            boolean web = screen.screenType == ScreenType.WEB_VIEW;
//            clazz1.setVisibility((web) ? View.VISIBLE : View.GONE);
//            main.setVisibility((web) ? View.GONE : View.VISIBLE);
            //mWebView.setVisibility((web) ? View.VISIBLE : View.GONE);
            //@@pb.setVisibility((web) && aaa.PROGRESSBAR_ENABLED ? View.VISIBLE : View.GONE);
            externalSetter(screen);
        }
    }

    @Override
    public boolean checkEmulator() {
        return false;
    }


    protected void externalSetter(Dataset screen) {
        //unlocked = screen.isEnabled();
        webview_external = screen.webview_external;
        DLog.d("@EX@" + webview_external);
    }

    protected abstract boolean enableHomeWebView();


    @Override
    public Integer orientation404() {
        return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    @Override
    public Integer orientationWeb() {
        return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    //@Override
    public boolean checkUpdate() {
        return false;
    }

    @Override
    public boolean webTitle() {
        return false;
    }

    @Override
    public boolean handleDeepLink() {
        return false;
    }

    @Override
    public void PEREHOD_S_DEEPLINKOM() {

    }

    @Override
    public AbstractDatasetRepository makeTracker() {
        return new FirebaseRepository(this);
    }


    public GConfig GCfc() {
        return new GConfig(
                true,
                false,
                UrlSaver.OH_NONE,
                true,
                true
        );
    }


    public boolean isWebViewExternal() {
        DLog.d("@@@@@@@@" + webview_external);
        return webview_external;
    }

    protected static boolean webview_external;


    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume(getIntent());
        //AboutBox.privacy_dialog_request(this);


//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        String muted = sharedPreferences.getString(KEY_MUTED, null);
//        if (muted == null) {
//            if (BuildConfig.DEBUG) {
//                //Toast.makeText(this, "Application first launch", Toast.LENGTH_SHORT).show();
//            }
//            //DLog.d(new GsonBuilder().create().toJson(new SystemEnvironment()));
//            Thread t = new Thread(() -> {
//                try {
//                    String am = new WebView(this).getSettings().getUserAgentString();
//                    SystemEnvironment me = new SystemEnvironment(am);
//                    DatabaseReference reference = FirebaseDatabase.getInstance()
//                            .getReference("users").child(me.GUID);
//                    reference.setValue(me);
//
//                } catch (Exception e) {
//                    DLog.handleException(e);
//                }
//            });
//            t.start();
//        }
    }


//    private void privacy_dialog_request(Context context) {
////        dialog = new AlertDialog.Builder(context)
////                .setTitle(R.string.app_name)
////                .setMessage(spannable)
////                .setIcon(R.mipmap.ic_launcher)
////                .setCancelable(false)
////                .setNegativeButton("No",
////                        (dialog, id) -> {
////                            dialog.cancel();
////                            finish();
////                        })
////                .setPositiveButton(R.string.action_continue,
////                        (dialog, id) -> dialog.cancel())
////                .create();
////
////        TextView aa = ((TextView) dialog.findViewById(android.R.id.message));
////        if (aa != null) {
////            aa.setMovementMethod(LinkMovementMethod.getInstance());
////            aa.setHighlightColor(Color.BLACK);// background color when pressed
////            aa.setLinkTextColor(Color.RED);
////        }
////        if (dialog != null && !dialog.isShowing()) {
////            dialog.show();
////        }
//    }


    @Override
    public void replaceFragment(Fragment fragment) {
        navigation.replaceFragment(fragment, R.id.container);
    }

    @Override
    public void setActionBarTitle(String title) {

    }

//    @Override
//    public boolean isUnlocked() {
//        //boolean isUnlocked = !pref.appWebDisabler(this);
//        //DLog.d("@@@"+isUnlocked);
//        return isUnlocked;
//    }



//    @Override
//    public void openOfferRequest(Category category) {
//
//        if (!isUnlocked() || TextUtils.isEmpty(category.url)) {
//            replaceFragment(OfferDescriptionFragment.newInstance(-1, category));
//            //category.detail != null
//            //"EVENT_INTERNAL"
//        } else {
//            openBrowser(category);//"EVENT_WEBVIEW"
//        }
//
////        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
////        Bundle bundle = new Bundle();
////        bundle.putString("WEB_OPEN", "" + isUnlocked());
////        mFirebaseAnalytics.logEvent("in_web_open_click", bundle);
//
//        offerPresetEvent(category);
//        trackClickOffer(category);
//    }

//    public void trackClickOffer(Category category) {
////        WebView mView = new WebView(this);
////        String tmp = mView.getSettings().getUserAgentString();
////        tmp = tmp.replace("; wv)", ")");
////
////        Map<String, String> params = new HashMap<>();
////        params.put("user_agent", tmp);
////        params.put("model", Build.MODEL);
////        params.put("board", Build.BOARD);
////        params.put(FirebaseAnalytics.Param.ITEM_ID, "[*]" + category._id);
////        params.put(FirebaseAnalytics.Param.ITEM_NAME, "[*]" + category.name);
////        params.put(FirebaseAnalytics.Param.CONTENT_TYPE, "CREDIT_OFFER");
////        MyTracker.trackEvent("click_offer", params);
//    }

//    private void offerPresetEvent(Category category) {
////        Bundle bundle = new Bundle();
////        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, /*id*/"id");
////        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, /**/"name");
////        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
////        FirebaseAnalytics.getInstance(this).logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
//
////        Bundle bundle = new Bundle();
////        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, String.valueOf(0));
////        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "init");
////        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
////        FirebaseAnalytics.getInstance(this).logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
//
//        Bundle params = new Bundle();
//        params.putString(FirebaseAnalytics.Param.ITEM_ID, "[*]" + category._id);
//        params.putString(FirebaseAnalytics.Param.ITEM_NAME, "[*]" + category.name);
//        params.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "CREDIT_OFFER");
//        FirebaseAnalytics.getInstance(this).logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, params);
//    }


//    public void openBrowser(Category category) {
//        if (isWebViewExternal()) {
//            String url = category.url;
//            if (!url.startsWith("http://") && !url.startsWith("https://")) {
//                url = "http://" + url;
//            }
//            DLog.d("@" + category.url);
//            ArrayList<ResolveInfo> list = Helpers.getCustomTabsPackages(this);

//            if (list.size() > 0) {
//                presenter.customTabsIntentLaunchUrl(url);
//            } else {
//                Helpers.openExternalBrowser(this, category);
//            }
//        } else {
//            replaceFragment(WebFragment.newInstance(category.url, aaa));
//        }
//    }

//    @Override
//    public void showLoading() {
//    }
//
//    @Override
//    public void hideLoading() {
//        if (swipeRefreshLayout.isRefreshing()) {
//            swipeRefreshLayout.setRefreshing(false);
//        }
//    }


//    @Override
//    public void onBackPressed() {
//
//        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            drawerLayout.closeDrawer(GravityCompat.START);
//            return;
//        }
//
//        if (__mView.canGoBack()) {
//            __mView.goBack();
//        } else {
//
//
//            //Log.d(TAG, "onBackPressed: " + getSupportFragmentManager().getBackStackEntryCount());
//
//
//            //Pressed back => return to home screen
//            int count = getSupportFragmentManager().getBackStackEntryCount();
//            if (getSupportActionBar() != null) {
//                getSupportActionBar().setHomeButtonEnabled(count > 0);
//            }
//            if (count > 0) {
//                getSupportFragmentManager().popBackStack(getSupportFragmentManager().getBackStackEntryAt(0).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
//            } else {//count == 0
//
////                Dialog
////                new AlertDialog.Builder(this)
////                        .setIcon(android.R.drawable.ic_dialog_alert)
////                        .setTitle("Leaving this App?")
////                        .setMessage("Are you sure you want to close this application?")
////                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
////                            @Override
////                            public void onClick(DialogInterface dialog, int which) {
////                                finish();
////                            }
////
////                        })
////                        .setNegativeButton("No", null)
////                        .show();
//                //super.onBackPressed();
//
//
//                if (doubleBackToExitPressedOnce) {
//                    super.onBackPressed();
//                    try {
//                        this.finish();
//                    } catch (Exception ignored) {
//                    }
//                    return;
//                }
//
//                this.doubleBackToExitPressedOnce = true;
//                Toast.makeText(this, R.string.press_again_to_exit, Toast.LENGTH_SHORT).show();
//
//                new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 800);
//
//            }
//
//
//            /*
//            //Next/Prev Navigation
//            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
//            new AlertDialog.Builder(this)
//                        .setIcon(android.R.drawable.ic_dialog_alert)
//                        .setTitle("Leaving this App?")
//                        .setMessage("Are you sure you want to close this application?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                finish();
//                            }
//
//                        })
//                        .setNegativeButton("No", null)
//                        .show();
//            }
//            else
//            {
//            super.onBackPressed();
//            }
//            */
//
//        }
//    }

    public void launch(String url) {
        DLog.d("00000000000000000000000000000000000000000");
//        __mView.post(() -> __mView.loadUrl(url));
    }


    @Override
    public void dappend(String var0) {
        DLog.d("@@@@@@@@@@@" + var0 + "\n");
    }

//    protected void actionRefresh() {
//        String url = __mView.getUrl();
//        if (url != null) {
//            __mView.reload();
//            //getContent(url);
//            Snackbar.make(getWindow().getDecorView(), /*url*/"Data updated.", Snackbar.LENGTH_SHORT).setAction(android.R.string.ok, null).show();
//        }
//    }

//    private void drawer() {
//        navigationView = findViewById(R.id.nav_view);
//        View headerView = navigationView.getHeaderView(0);
//        //TextView appName = headerView.findViewById(R.id.appName);
//        TextView appVersion = headerView.findViewById(R.id.appVersion);
//        appVersion.setText(DLog.getAppVersion(this));
//
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();
//
//
//        navigationView.setNavigationItemSelectedListener(item -> {
//            // Handle navigation view item clicks here.
//            int id = item.getItemId();
//            loadHomeFragment(item);
//            DrawerLayout drawer0 = findViewById(R.id.drawer_layout);
//            drawer0.closeDrawer(GravityCompat.START);
//            return true;
//        });
//    }



    @Override
    public void onRefresh() {
//        __mView.reload();
//        if (swipeRefreshLayout.isRefreshing()) {
//            swipeRefreshLayout.setRefreshing(false);
//        }
    }

    @Override
    public boolean rotated() {
        return rotated0;
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        outState.putBoolean(P.KEY_ROTATED, rotated0);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        rotated0 = savedInstanceState.getBoolean(P.KEY_ROTATED, false);
    }

    @Override
    protected void onStop() {
        //swipeRefreshLayout.getViewTreeObserver().removeOnScrollChangedListener(mOnScrollChangedListener);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    public void nextScreen(int i) {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count > 0) {
            int mid = getSupportFragmentManager().getBackStackEntryAt(0).getId();
            //DLog.d("@@@@" + mid+" "+count);
            getSupportFragmentManager()
                    .popBackStack(mid, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }


}
