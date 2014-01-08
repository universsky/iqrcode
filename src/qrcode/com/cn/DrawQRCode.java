package qrcode.com.cn;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.swetake.util.Qrcode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

//import android.graphics.Color;


public class DrawQRCode extends Activity
        implements SurfaceHolder.Callback {
    private String TAG = "HIPPO";
    private SurfaceView mSurfaceView01;
    private SurfaceHolder mSurfaceHolder01;

    private TextView tv;
    private TextView tvFileName;


    /**
     * Called when the activity is first created.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //使应用程序全屏幕运行
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.drawqrcode);

        //获得屏幕解析像素
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //以SurfaceView作为相机Preview之用
        mSurfaceView01 = (SurfaceView) findViewById(R.id.mSurfaceView1);

        //绑定SurfaceView, 取得SurfaceHolder对象
        mSurfaceHolder01 = mSurfaceView01.getHolder();
        //Activity 必须实现SurfaceHolder.Callback ,receive information about changes to the surface
        mSurfaceHolder01.addCallback(DrawQRCode.this);


        Bundle bundle = this.getIntent().getExtras();
        char sort1 = bundle.getChar("sort1");
        tv = (TextView) findViewById(R.id.result);
        tvFileName = (TextView) findViewById(R.id.filename);

        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar = Calendar.getInstance();
        String filename = df.format(calendar.getTime()) + "_iqrcode.jpg";

        tvFileName.setText(filename);

        switch (sort1) {
            case 'N': {
                String sort = bundle.getString("sort");
                String name = bundle.getString("name");
                String occupation = bundle.getString("occupation");
                String fixedphone = bundle.getString("fixedphone");
                String mobilephone = bundle.getString("mobilephone");
                tv.setText("您的" + sort + "信息如下: " + "\n姓名: " + name + "\n职业: " + occupation + "\n固定电话: " + fixedphone + "\n移动电话: " + mobilephone);

                break;
            }
            case 'M': {
                String sort = bundle.getString("sort");
                String phonenumber = bundle.getString("phonenumber");
                String text = bundle.getString("text");
                tv.setText("您的" + sort + "信息如下: " + "\n收件人: " + phonenumber + "\n内容: " + text);
                break;
            }
            case 'E': {
                String sort = bundle.getString("sort");
                String email = bundle.getString("email");
                String theme = bundle.getString("theme");
                String text = bundle.getString("text");
                tv.setText("您的" + sort + "信息如下: " + "\n收件人:" + email + "\n主题: " + theme + "\n内容: " + text);
                break;
            }
            case 'T': {
                String sort = bundle.getString("sort");
                String title = bundle.getString("title");
                String text = bundle.getString("text");
                tv.setText("您的" + sort + "信息如下: " + "\n标题: " + title + "\n内容: " + text);
                break;
            }
            case 'U': {
                String sort = bundle.getString("sort");
                String title = bundle.getString("title");
                String url = bundle.getString("url");
                tv.setText("您的" + sort + "信息如下:" + "\n标题:" + title + "\n链接:" + url);
                break;
            }
            case 'F': {
                String free = bundle.getString("freeContent");
                tv.setText(free);
                break;
            }
            default:
                break;

        }

        // Draw QRCode二维码图

        Button draw = (Button) findViewById(R.id.draw);
        draw.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                AndroidQREncode(tv.getText().toString(), 10);
            }
        });


        //保存图片到sd卡
        Button save = (Button) findViewById(R.id.save);

        save.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertSave();


                /**
                 View view = getWindow().getDecorView().findViewById(R.id.mSurfaceView1);
                 //              Display display = DrawQRCode.this.getWindowManager().getDefaultDisplay();
                 //              view.layout(0, 0, display.getWidth(), display.getHeight());
                 view.setDrawingCacheEnabled(true);

                 AndroidQREncode(tv.getText().toString(),10);

                 Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());

                 DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                 Calendar calendar = Calendar.getInstance();

                 String imagename = df.format(calendar.getTime())+"_iqrcode.jpg";
                 String path = Environment.getExternalStorageDirectory().getPath()+"/iqrcode";

                 File f = new File(path + imagename);
                 if(!f.exists()){
                 f.mkdir();
                 }

                 try{
                 f.createNewFile();
                 }catch (IOException e){
                 e.printStackTrace();
                 }


                 FileOutputStream fout = null;
                 bitmap.compress(Bitmap.CompressFormat.PNG,100, fout);

                 try{
                 fout = new FileOutputStream(f);
                 }catch (FileNotFoundException e){
                 e.printStackTrace();
                 }

                 try{
                 fout.close();
                 }catch (IOException e){
                 e.printStackTrace();
                 }
                 */
            }

        });


        //返回选择界面
        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.setClass(DrawQRCode.this, iCQRcode.class);
                startActivity(intent);
                DrawQRCode.this.finish();

            }
        });


    }


    public void alertSave() {

        LayoutInflater inflater = (LayoutInflater) DrawQRCode.this.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.edittext, null);

        EditText editFileName = (EditText) view.findViewById(R.id.save_filename);

        TextView filenameText = (TextView) findViewById(R.id.filename);
        String filename = filenameText.getText().toString();

        editFileName.setText(filename);

        new AlertDialog.Builder(DrawQRCode.this).setTitle("保存图片")
                .setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int intNum) {

                        TextView filenameText = (TextView) findViewById(R.id.filename);
                        String filename = filenameText.getText().toString();

                        Qrcode qrcode = new Qrcode();
                        qrcode.setQrcodeErrorCorrect('M');
                        qrcode.setQrcodeEncodeMode('B');
                        qrcode.setQrcodeVersion(12);

                        String srcString = tv.getText().toString();


                        try {

                            byte[] d = srcString.getBytes("utf-8");
                            boolean[][] b = qrcode.calQrcode(d);

                            int width = b.length * 3 + 5;
                            int height = b.length * 3 + 5;
                            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

                            Canvas g = new Canvas(bitmap);// Canvas(Bitmap bitmap): 以bitmap对象创建一个画布，则将内容都绘制在bitmap上，因此bitmap不得为null。

                            g.drawColor(getResources().getColor(R.drawable.white));
                            Paint paint = new Paint();
                            paint.setStyle(Paint.Style.FILL);
                            paint.setColor(R.drawable.black);
                            paint.setStrokeWidth(1.0F);


                            for (int i = 0; i < b.length; i++) {
                                for (int j = 0; j < b.length; j++) {
                                    if (b[j][i]) {
                                        g.drawRect(new Rect(3 * j + 2, 3 * i + 2, j * 3 + 2 + 3, i * 3 + 2 + 3), paint);
                                    }
                                }
                            }

                            // Debug useage

                            String bStr = bitmap.toString();
                            int bWidth = bitmap.getWidth();
                            int bHeight = bitmap.getHeight();
                            int bByteCount = bitmap.getByteCount();
                            String sdstate = Environment.getExternalStorageState();
                            String sdpath = Environment.getExternalStorageDirectory().getPath();

                            String imagepath = sdpath + "/iqrcode";

                            String msg = bStr + "\n" +
                                    "bWidth: " + bWidth + "\n" +
                                    "bHeight: " + bHeight + "\n" +
                                    "bByteCount: " + bByteCount + "\n" +
                                    "image: " + filename + "\n" +
                                    "sdstate: " + sdstate + "\n" +
                                    "sdpath: " + sdpath + "\n" +
                                    "imagepath: " + imagepath;


                            /**
                             *  Debug useage
                             */
                            new AlertDialog.Builder(DrawQRCode.this).setMessage(msg).show();


                            //得到外部存储卡sdcard的路径
                            //String path=Environment.getExternalStorageDirectory().toString();
                            //filename是将要存储的图片的名称

                            File file = new File(imagepath, filename);
                            //if(!file.exists()) file.mkdir();
                            //从资源文件中选择一张图片作为将要写入的源文件
                            //Bitmap bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.ss);
                            try {
                                FileOutputStream out = new FileOutputStream(file);
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                                out.flush();
                                out.close();
                                String successMsg = "Saved Successfully";
                                new AlertDialog.Builder(DrawQRCode.this).setMessage(successMsg).show();
                            } catch (Exception e) {

                                e.printStackTrace();
                            }

                            //saveSDCard(bitmap, filename);


                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        //获取SurfaceView区的图片,Bitmap格式
                        //Bitmap bitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
//
//            int pixels[] = new int[width*height];
//            int offset;
//            int stride;
//            int x;
//            int y;
//
//            bitmap.setPixels(pixels,offset,stride,x,y,width,height);


                        //Canvas canvas = new Canvas(bitmap);
                        //mSurfaceView01.draw(canvas);
                        //canvas.drawBitmap();

                        //保存到SD卡iqrcode路径下

                        //saveSDCard(bitmap, filename);

                        /**
                         * 使用surfaceview来进行截图. 正常情况下,
                         * 图像是绘制在
                         * canvas = mHolder.lockCanvas()
                         * 这张画布上的,
                         * 所以你才能在屏幕上看到图像.
                         * 当你需要截图的时候,可以使用一张bitmap来new一张画布,
                         * 将图像绘制在这张画布上,
                         * 然后再使用bitmap.compress()方法,就能将bitmap转换为图片了.
                         */

                        //Toast.makeText(DrawQRCode.this, filename , Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(DrawQRCode.this, "You canceled saving", Toast.LENGTH_LONG).show();

                    }

                }).show();


    }


    //保存文件

    public void saveSDCard(Bitmap bitmap, String imagename) {


        boolean bSDCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);

        if ( bSDCardExist ) {

            String sdpath = Environment.getExternalStorageDirectory().getPath();

            String path = sdpath + "/iqrcode/";

            File f = new File(path, imagename);
            if (!f.exists()|| !f.isDirectory()){
                f.mkdir();
                throw new IllegalArgumentException(
                        "Image file path : sdpath/iqrcode must not be null! Already create this directory.");
            }
            //f.setWritable(true);
            //f.mkdir();

            try {
                if (f.createNewFile()) {
                    new AlertDialog.Builder(DrawQRCode.this).setMessage("CreateFile Successfully.").show();
                } else {
                    new AlertDialog.Builder(DrawQRCode.this).setMessage("CreateFile Failed.").show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            FileOutputStream fout = null;
            try {
                fout = new FileOutputStream(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fout);
            } else {
                new AlertDialog.Builder(DrawQRCode.this)
                        .setMessage("bitmap is null").show();
            }

//            try {
//                fout = new FileOutputStream(f);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }

            try {
                fout.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                fout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            String message = Environment.getExternalStorageState() + " : " + "SD Card Unavailable";
            new AlertDialog.Builder(DrawQRCode.this).
                    setMessage(message).show();
        }


    }

    //自定义QRCode函数
    public void AndroidQREncode(String strEncoding, int qrcodeVersion) {
        try {
            //构建QRCode编码对象
            com.swetake.util.Qrcode testQrcode = new com.swetake.util.Qrcode();
      
            /* L','M','Q','H' */
            testQrcode.setQrcodeErrorCorrect('M');
            /* "N","A" or other */
            testQrcode.setQrcodeEncodeMode('B');
            /* 0-20 */
            testQrcode.setQrcodeVersion(qrcodeVersion);

            // getBytes
            byte[] bytesEncoding = strEncoding.getBytes("utf-8");

            if (bytesEncoding.length > 0) {
                //转化成boolean数组
                boolean[][] bEncoding = testQrcode.calQrcode(bytesEncoding);

                //绘图
                drawQRCode(bEncoding, getResources().getColor(R.drawable.black));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //在SurfaceView上绘制QRCode条形码
    private void drawQRCode(boolean[][] bRect, int colorFill) {
    /* Draw On Canvas*/

        int intPadding = 20;

        //绘图前先锁定Surfaceholder
        Canvas mCanvas01 = mSurfaceHolder01.lockCanvas();
        //设置画图绘图颜色
        mCanvas01.drawColor(getResources().getColor(R.drawable.white));

        //创建画笔
        Paint mPaint01 = new Paint();

        //设置画笔颜色和模式
        mPaint01.setStyle(Paint.Style.FILL);
        mPaint01.setColor(colorFill);
        mPaint01.setStrokeWidth(1.0F);

        //逐一加载boolean数组
        for (int i = 0; i < bRect.length; i++) {
            for (int j = 0; j < bRect.length; j++) {
                if (bRect[j][i]) {
                    //绘出条形码方块
                    mCanvas01.drawRect
                            (
                                    new Rect
                                            (
                                                    intPadding + j * 3 + 2,
                                                    intPadding + i * 3 + 2,
                                                    intPadding + j * 3 + 2 + 3,
                                                    intPadding + i * 3 + 2 + 3
                                            ), mPaint01
                            );
                }
            }
        }
        //解锁并绘图
        mSurfaceHolder01.unlockCanvasAndPost(mCanvas01);
    }


    private byte[] Bitmap2Bytes(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }


    @Override
    public void surfaceChanged
            (SurfaceHolder surfaceholder, int format, int w, int h) {
        // TODO Auto-generated method stub
        Log.i(TAG, "Surface Changed");
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceholder) {
        // TODO Auto-generated method stub
        Log.i(TAG, "Surface Changed");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceholder) {
        // TODO Auto-generated method stub
        Log.i(TAG, "Surface Destroyed");
    }
}
