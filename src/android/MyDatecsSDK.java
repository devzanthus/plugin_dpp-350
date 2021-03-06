package br.com.mytdt.print;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.LOG;

import org.json.JSONArray;
import org.json.JSONException;
import android.util.Log;
import android.content.Context;
import android.telephony.TelephonyManager;

public class MyDatecsSDK extends CordovaPlugin {
    public static final String ACTION_CONNECT = "connect";
    public static final String ACTION_DISCONNECT = "disconnect";
    public static final String ACTION_INIT = "init";
    public static final String ACTION_FINISH = "finish";
    public static final String ACTION_RESET = "reset";
    public static final String ACTION_PRINT_TEXT = "printText";
    public static final String ACTION_PRINT_TAGGED_TEXT = "printTaggedText";
    public static final String ACTION_FEED_PAPER = "feedPaper";
    public static final String ACTION_FLUSH = "flush";
    public static final String ACTION_SELECT_PAGE_MODE = "selectPageMode";
    public static final String ACTION_SELECT_STANDARD_MODE = "selectStandardMode";
    public static final String ACTION_PRINT_PAGE = "printPage";
    public static final String ACTION_SET_ALIGN = "setAlign";
    public static final String ACTION_PRINT_SELF_TEST = "printSelfTest";
    public static final String ACTION_BARCODE = "printBarcode";
    public static final String ACTION_IMAGE = "printImage";
    
    private static final MyPrinter myPrinter = new MyPrinter();

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        TelephonyManager telephonyManager = (TelephonyManager) cordova.getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        final String imei = telephonyManager.getDeviceId();

            if (ACTION_CONNECT.equals(action)) {
                String address = args.getString(0);
                this.connect(address, callbackContext);
                return true;
            } else if (ACTION_DISCONNECT.equals(action)) {
                this.disconnect(callbackContext);
                return true;
            } else if (ACTION_INIT.equals(action)) {
                this.init(callbackContext);
                return true;
            } else if (ACTION_FINISH.equals(action)) {
                this.finish(callbackContext);
                return true;
            } else if (ACTION_RESET.equals(action)) {
                this.reset(callbackContext);
                return true;
            } else if (ACTION_PRINT_TEXT.equals(action)) {
                String text = args.getString(0);
                String charset = args.getString(1);
                this.printText(text, charset, callbackContext);
                return true;
            } else if (ACTION_PRINT_TAGGED_TEXT.equals(action)) {
                String text = args.getString(0);
                String charset = args.getString(1);
                this.printTaggedText(text, charset, callbackContext);
                return true;
            } else if (ACTION_FEED_PAPER.equals(action)) {
                int lines = args.getInt(0);
                this.feedPaper(lines, callbackContext);
                return true;
            } else if (ACTION_FLUSH.equals(action)) {
                this.flush(callbackContext);
                return true;
            } else if (ACTION_SELECT_PAGE_MODE.equals(action)) {
                this.selectPageMode(callbackContext);
                return true;
            } else if (ACTION_SELECT_STANDARD_MODE.equals(action)) {
                this.selectStandardMode(callbackContext);
                return true;
            } else if (ACTION_PRINT_PAGE.equals(action)) {
                this.printPage(callbackContext);
                return true;
            } else if (ACTION_SET_ALIGN.equals(action)) {
                String align = args.getString(0);
                this.setAlign(align, callbackContext);
                return true;
            }else if (ACTION_PRINT_SELF_TEST.equals(action)) {
                this.printSelfTest(callbackContext);
                return true;
            }else if(ACTION_BARCODE.equals(action)){
            	 String qrcode = args.getString(0);
            	this.printBarcode(qrcode, callbackContext);
            	return true;
            }else if(ACTION_IMAGE.equals(action)){
	           	String imagem = args.getString(0);
	           	this.printImage(imagem, callbackContext);
           	return true;
           }
        return false;
    }

    private void connect(final String address, final CallbackContext callbackContext) {
        final CordovaInterface myCordova = cordova;
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                if ((address != null) && (address.length() > 0)) {
                    try {
                        myPrinter.setCordova(myCordova);
                        myPrinter.setAddress(address);
                        myPrinter.connect(callbackContext);
                    } catch (Exception e) {
                        e.printStackTrace();
                        callbackContext.error(e.getMessage());
                    }
                } else {
                    callbackContext.error("Informe o endereço do dispositivo.");
                }
            }
        });
    }

    private void disconnect(final CallbackContext callbackContext) {
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                try {
                    myPrinter.disconnect(callbackContext);
                } catch (Exception e) {
                    e.printStackTrace();
                    callbackContext.error(e.getMessage());
                }
            }
        });
    }

    private void init(final CallbackContext callbackContext) {
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                try {
                    myPrinter.init(callbackContext);
                } catch (Exception e) {
                    e.printStackTrace();
                    callbackContext.error(e.getMessage());
                }
            }
        });
    }

    private void finish(final CallbackContext callbackContext) {
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                try {
                    myPrinter.finish(callbackContext);
                } catch (Exception e) {
                    e.printStackTrace();
                    callbackContext.error(e.getMessage());
                }
            }
        });
    }

    private void reset(final CallbackContext callbackContext) {
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                try {
                    myPrinter.reset(callbackContext);
                } catch (Exception e) {
                    e.printStackTrace();
                    callbackContext.error(e.getMessage());
                }
            }
        });
    }

    private void printText(final String text, final String charset, final CallbackContext callbackContext) {
    	cordova.getThreadPool().execute(new Runnable(){
            public void run() {
                if ((text != null) && (text.length() > 0)) {
                    String myCharset = "CP1252";
                    if ((charset != null) && (charset.length() > 0)) {
                        if (!charset.equals("null")) {
                            myCharset = charset;
                        }
                    }
                    try {
                        myPrinter.printText(text, myCharset, callbackContext);
                    } catch (Exception e) {
                        e.printStackTrace();
                        callbackContext.error(e.getMessage());
                    }
                } else {
                    callbackContext.error("Informe o texto a ser impresso.");
                }
            }
        });
    }

    private void printTaggedText(final String text, final String charset, final CallbackContext callbackContext) {
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                if ((text != null) && (text.length() > 0)) {
                    String myCharset = "CP1252";
                    if ((charset != null) && (charset.length() > 0)) {
                        if (!charset.equals("null")) {
                            myCharset = charset;
                        }
                    }
                    try {
                        myPrinter.printTaggedText(text, myCharset, callbackContext);
                    } catch (Exception e) {
                        e.printStackTrace();
                        callbackContext.error(e.getMessage());
                    }
                } else {
                    callbackContext.error("Informe o texto a ser impresso.");
                }
            }
        });
    }

    private void feedPaper(final int lines, final CallbackContext callbackContext) {
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                if ((lines > -1) && (lines < 256)) {
                    try {
                        myPrinter.feedPaper(lines, callbackContext);
                    } catch (Exception e) {
                        e.printStackTrace();
                        callbackContext.error(e.getMessage());
                    }
                } else {
                    callbackContext.error("Informe a quantidade de linhas de 0 a 255.");
                }
            }
        });
    }

    private void flush(final CallbackContext callbackContext) {
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                try {
                    myPrinter.flush(callbackContext);
                } catch (Exception e) {
                    e.printStackTrace();
                    callbackContext.error(e.getMessage());
                }
            }
        });
    }

    private void selectPageMode(final CallbackContext callbackContext) {
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                try {
                    myPrinter.selectPageMode(callbackContext);
                } catch (Exception e) {
                    e.printStackTrace();
                    callbackContext.error(e.getMessage());
                }
            }
        });
    }

    private void selectStandardMode(final CallbackContext callbackContext) {
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                try {
                    myPrinter.selectStandardMode(callbackContext);
                } catch (Exception e) {
                    e.printStackTrace();
                    callbackContext.error(e.getMessage());
                }
            }
        });
    }

    private void printPage(final CallbackContext callbackContext) {
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                try {
                    myPrinter.printPage(callbackContext);
                } catch (Exception e) {
                    e.printStackTrace();
                    callbackContext.error(e.getMessage());
                }
            }
        });
    }

    private void setAlign(final String align, final CallbackContext callbackContext) {
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                if ((align != null) && (align.length() > 0)) {
                    try {
                        int myAlign = -1;
                        String strAlign = align.toLowerCase();
                        if (strAlign.equals("left")) {
                            myAlign = 0;
                        } else if (strAlign.equals("center")) {
                            myAlign = 1;
                        } else if (strAlign.equals("right")) {
                            myAlign = 2;
                        }
                        if (myAlign != -1) {
                            myPrinter.setAlign(myAlign, callbackContext);
                        } else {
                            callbackContext.error("Informe um dos alinhamentos possíveis: left, center, right.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        callbackContext.error(e.getMessage());
                    }
                } else {
                    callbackContext.error("Informe o alinhamento.");
                }
            }
        });
    }

    private void printSelfTest(final CallbackContext callbackContext) {
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                try {
                    myPrinter.printSelfTest(callbackContext);
                } catch (Exception e) {
                    e.printStackTrace();
                    callbackContext.error(e.getMessage());
                }
            }
        });
    }
    
    private void printBarcode(final String qrcode, final CallbackContext callbackContext) {
    	cordova.getThreadPool().execute(new Runnable(){
            public void run() {
                try {
                	myPrinter.printBarcode(qrcode, callbackContext); 
                } catch (Exception e) {
                    e.printStackTrace();
                    callbackContext.error(e.getMessage());
                }
            }
        });
    }

    private void printImage(final String imagem, final CallbackContext callbackContext) {
         cordova.getActivity().runOnUiThread(new Runnable() {
             public void run() {
                 try {
                 	myPrinter.printImage(imagem, callbackContext); 
                 } catch (Exception e) {
                     e.printStackTrace();
                     callbackContext.error(e.getMessage());
                 }
             }
         });
     }
    
}
