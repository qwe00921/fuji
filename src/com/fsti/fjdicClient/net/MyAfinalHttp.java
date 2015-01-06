package com.fsti.fjdicClient.net;


//public class MyAfinalHttp {
//
//    private static MyAfinalHttp myAfinalHttp;
//
//    private final int           timeOut                    = 10000;
//    private final int           requestExecutionRetryCount = 1;
//
//    public static MyAfinalHttp getInstance() {
//
//        if (myAfinalHttp == null) {
//            myAfinalHttp = new MyAfinalHttp();
//        }
//        return myAfinalHttp;
//    }
//
//    /**
//     * 初始化FinalHttp的超时时间和重复次数
//     * 
//     * @return
//     */
//    public FinalHttp getFinalHttp() {
//        FinalHttp finalHttp = new FinalHttp();
//        finalHttp.configTimeout(timeOut);
//        finalHttp.configRequestExecutionRetryCount(requestExecutionRetryCount);
//        return finalHttp;
//    }
//
//    /**
//     * 用于添加公共参数
//     * 
//     * @param params
//     * @return
//     */
//    private AjaxParams initParams(String methodName, Map<String, Object> map) {
//        AjaxParams params = new AjaxParams();
//        JSONObject json = new JSONObject(map);
//        params.put("param", json.toString());
//        params.put("api", methodName);
//        params.put("v", "1");
//        params.put("auth", "1111111");
//        // params.put("AuthInfo",
//        // "{\"ClientId\":\"9000023\",\"ClientVersion\":\"\",\"Sign\":\"\",\"SessionKey\":\"\"}");
//        return params;
//    }
//
//    /**
//     * @方法名称: finalPost
//     * @功能描述: TODO
//     * @参数 @param url
//     * @参数 @param 方法名
//     * @参数 @param 参数
//     * @参数 @param callBack
//     * @返回类型 void
//     * @调用举例:
//     * @修改: DonghuiWu
//     */
//    public void finalPost(final String methodName, Map<String, Object> map, final AjaxCallBack<Result> callBack) {
//        AjaxParams params = initParams(methodName, map);
////        Log.d("debug", methodName + " = " + Constant.MAIN_URL + "?" + params.toString());
//        getFinalHttp().post(Constant.MAIN_URL, params, new AjaxCallBack<Object>() {
//
//            @Override
//            public void onStart() {
//                super.onStart();
//                if (callBack != null) {
//                    callBack.onStart();
//                }
//            }
//
//            @Override
//            public void onSuccess(Object t) {
//                super.onSuccess(t);
//                if (callBack != null) {
//                    Result result = new Result(t.toString());
//                    callBack.onSuccess(result);
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable t, int errorNo, String strMsg) {
//                super.onFailure(t, errorNo, strMsg);
//                if (callBack != null) {
//                    callBack.onFailure(t, errorNo, strMsg);
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable t, String strMsg) {
//                super.onFailure(t, strMsg);
//                System.out.println("网络异常：" + strMsg);
//                if (callBack != null) {
//                    callBack.onFailure(t, strMsg);
//                }
//            }
//        });
//    }
//
//    /**
//     * @方法名称: finalPost
//     * @功能描述: TODO
//     * @参数 @param url
//     * @参数 @param 方法名
//     * @参数 @param 参数
//     * @参数 @param callBack
//     * @返回类型 void
//     * @调用举例:
//     * @修改: DonghuiWu
//     */
//    public void finalPost(String url, final String methodName, AjaxParams params, final AjaxCallBack<Result> callBack) {
//        Log.d("debug", methodName + " = " + Constant.MAIN_URL + "?" + params.toString());
//        getFinalHttp().post(url, params, new AjaxCallBack<Object>() {
//
//            @Override
//            public void onStart() {
//                super.onStart();
//                if (callBack != null) {
//                    callBack.onStart();
//                }
//            }
//
//            @Override
//            public void onSuccess(Object t) {
//                super.onSuccess(t);
//                if (callBack != null) {
//                    Result result = new Result(t.toString());
//                    callBack.onSuccess(result);
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable t, int errorNo, String strMsg) {
//                super.onFailure(t, errorNo, strMsg);
//                if (callBack != null) {
//                    callBack.onFailure(t, errorNo, strMsg);
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable t, String strMsg) {
//                super.onFailure(t, strMsg);
//                System.out.println("网络异常：" + strMsg);
//                if (callBack != null) {
//                    callBack.onFailure(t, strMsg);
//                }
//            }
//        });
//    }
//
//    /**
//     * @功能描述: TODO 测试
//     */
//    public void finalPostTest(final String methodName, Map<String, Object> map, final AjaxCallBack<Result> callBack) {
//        AjaxParams params = initParams(methodName, map);
//        Log.d("debug", methodName + " = " + Constant.MAIN_URL + "?" + params.toString());
//        getFinalHttp().post(Constant.TEST_URL, params, new AjaxCallBack<Object>() {
//
//            @Override
//            public void onStart() {
//                super.onStart();
//                if (callBack != null) {
//                    callBack.onStart();
//                }
//            }
//
//            @Override
//            public void onSuccess(Object t) {
//                super.onSuccess(t);
//                if (callBack != null) {
//                    Result result = new Result(t.toString());
//                    callBack.onSuccess(result);
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable t, int errorNo, String strMsg) {
//                super.onFailure(t, errorNo, strMsg);
//                if (callBack != null) {
//                    callBack.onFailure(t, errorNo, strMsg);
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable t, String strMsg) {
//                super.onFailure(t, strMsg);
//                if (callBack != null) {
//                    callBack.onFailure(t, strMsg);
//                }
//            }
//        });
//    }
//
//    public void finalGet(final String url, final String methodName, HashMap<String, Object> map, final AjaxCallBack<Result> callBack) {
//        AjaxParams params = initParams(methodName, map);
//        Log.d("debug", methodName + " = " + Constant.MAIN_URL + "?" + params.toString());
//        getFinalHttp().get(url, params, new AjaxCallBack<Object>() {
//
//            @Override
//            public void onStart() {
//                super.onStart();
//                if (callBack != null) {
//                    callBack.onStart();
//                }
//            }
//
//            @Override
//            public void onSuccess(Object t) {
//                super.onSuccess(t);
//                if (callBack != null) {
//                    Result result = new Result(t.toString());
//                    callBack.onSuccess(result);
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable t, int errorNo, String strMsg) {
//                super.onFailure(t, errorNo, strMsg);
//                if (callBack != null) {
//                    callBack.onFailure(t, errorNo, strMsg);
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable t, String strMsg) {
//                super.onFailure(t, strMsg);
//                if (callBack != null) {
//                    callBack.onFailure(t, strMsg);
//                }
//            }
//        });
//    }
// }
