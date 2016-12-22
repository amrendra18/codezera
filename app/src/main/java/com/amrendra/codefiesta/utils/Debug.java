package com.amrendra.codefiesta.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.BuildConfig;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Amrendra Kumar on 05/04/16.
 */
public class Debug {
    private static final Boolean DEBUG = BuildConfig.DEBUG;
    private static final String TAG = "bdebug";

    public static void forceCrash() {
        if (DEBUG) {
            throw new RuntimeException("Cheeky!! This is a simulated debug crash");
        }
    }

    private static String getMsg(String msg) {
        if (DEBUG) {
            StackTraceElement strace = Thread.currentThread().getStackTrace()[4];
            // String fileName = strace.getFileName();
            String className = strace.getClassName();
            //className = className.replace(packageName, "");
            String methodName = strace.getMethodName();
            int line = strace.getLineNumber();
            msg = className + "::" + methodName + "()[" + line + "]" + " : " + msg;
        }
        return msg;
    }

    public static void c() {
        if (DEBUG) {
            Log.e(TAG, getMsg("CHECK"));
        }
    }

    public static void e(String msg, Boolean show) {
        if (DEBUG || show) {
            Log.e(TAG, getMsg(msg));
        }
    }

    public static void d(String msg, Boolean show) {
        if (DEBUG || show) {
            Log.d(TAG, getMsg(msg));
        }
    }

    public static void i(String msg, Boolean show) {
        if (DEBUG || show) {
            Log.i(TAG, getMsg(msg));
        }
    }

    public static void w(String msg, Boolean show) {
        if (DEBUG || show) {
            Log.w(TAG, getMsg(msg));
        }
    }

    public static void v(String msg, Boolean show) {
        if (DEBUG || show) {
            Log.v(TAG, getMsg(msg));
        }
    }

    public static void showToastShort(String msg, Context context) {
        showToastShort(msg, context, false);
    }

    public static void showToastLong(String msg, Context context) {
        showToastLong(msg, context, false);
    }

    public static void showToastShort(String msg, Context context, Boolean show) {
        if (DEBUG || show) {
            if (context == null) {
                Log.e(TAG, getMsg("Context is null. Trying to show toast. Msg: " + msg));
            } else {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void showToastLong(String msg, Context context, Boolean show) {
        if (DEBUG || show) {
            if (context == null) {
                Log.e(TAG, getMsg("Context is null. Trying to show toast. Msg: " + msg));
            } else {
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
            }
        }
    }

    public static void array(Object[] arr) {
        if (DEBUG) {
            Log.i(TAG, getMsg(Arrays.deepToString(arr)));
        }
    }

    public static void object(Object obj, String desc) {
        if (DEBUG) {
            if (obj == null) {
                Log.e(TAG, getMsg("Error : " + desc + " is NULL"));
            } else {
                Log.e(TAG, getMsg(obj.toString()));
            }
        }
    }

    private static String debugObject(String key, Object obj) {
        if (obj == null) {
            return String.format("%s = <null>%n", key);
        }
        return String.format("%s = %s (%s)%n", key, String.valueOf(obj), obj.getClass()
                .getSimpleName());
    }

    public static void bundle(Bundle bundle) {
        if (DEBUG) {
            if (bundle != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Bundle{\n");
                for (String key : bundle.keySet()) {
                    sb.append(debugObject(key, bundle.get(key)));
                }
                sb.append("}");
                Log.d(TAG, getMsg(sb.toString()));
            } else {
                Log.e(TAG, getMsg("Error : Expected Bundle is null"));
            }
        }
    }

    public static void intent(Intent intent) {
        if (DEBUG) {
            if (intent != null) {
                bundle(intent.getExtras());
            } else {
                Log.d(TAG, getMsg("Error : Expected Intent is null"));
            }
        }
    }

    public static void preferences(SharedPreferences prefs) {
        if (DEBUG) {
            if (prefs != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("SharedPreferences{\n");
                for (Map.Entry<String, ?> entry : prefs.getAll().entrySet()) {
                    sb.append(debugObject(entry.getKey(), entry.getValue()));
                }
                sb.append("}");
                Log.d(TAG, getMsg(sb.toString()));
            } else {
                Log.e(TAG, getMsg("Error : Expected SharedPreferences is null"));
            }
        }
    }

    public static void JSONObject(JSONObject jsonObject) {
        if (DEBUG) {
            if (jsonObject != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("JsonObject{\n");
                Iterator<String> s = jsonObject.keys();
                while (s.hasNext()) {
                    String key = s.next();
                    try {
                        sb.append(debugObject(key, jsonObject.get(key)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                sb.append("}");
                Log.i(TAG, getMsg(sb.toString()));
            } else {
                Log.e(TAG, getMsg("Error : Expected JSONObject is null"));
            }
        }
    }
}
