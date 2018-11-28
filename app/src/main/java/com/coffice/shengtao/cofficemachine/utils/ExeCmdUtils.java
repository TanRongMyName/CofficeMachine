package com.coffice.shengtao.cofficemachine.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.lang.Runtime.getRuntime;

public class ExeCmdUtils {
    //shell进程
    private Process process;
    //对应进程的3个流
    private BufferedReader successResult;
    private BufferedReader errorResult;
    private DataOutputStream os;
    //是否同步，true：run会一直阻塞至完成或超时。false：run会立刻返回
    private boolean bSynchronous;
    //表示shell进程是否还在运行
    private boolean bRunning = false;
    //同步锁
    ReadWriteLock lock = new ReentrantReadWriteLock();

    //保存执行结果
    private StringBuffer result = new StringBuffer();

    /**
     * 构造函数
     *
     * @param synchronous true：同步，false：异步
     */
    public ExeCmdUtils(boolean synchronous) {
        bSynchronous = synchronous;
    }

    /**
     * 默认构造函数，默认是同步执行
     */
    public ExeCmdUtils() {
        bSynchronous = true;
    }

    /**
     * 还没开始执行，和已经执行完成 这两种情况都返回false
     *
     * @return 是否正在执行
     */
    public boolean isRunning() {
        return bRunning;
    }

    /**
     * @return 返回执行结果
     */
    public String getResult() {
        Lock readLock = lock.readLock();
        readLock.lock();
        try {
            LogUtils.i("auto", "getResult");
            return new String(result);
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 执行命令
     *
     * @param command eg: cat /sdcard/test.txt
     * 路径最好不要是自己拼写的路径，最好是通过方法获取的路径
     * example：Environment.getExternalStorageDirectory()
     * @param maxTime 最大等待时间 (ms)
     * @return this
     */
    public ExeCmdUtils run(String command, final int maxTime) {
        LogUtils.i("auto", "run command:" + command + ",maxtime:" + maxTime);
        if (command == null || command.length() == 0) {
            return this;
        }

        try {
            process = getRuntime().exec("su");//看情况可能是su  public static final String COMMAND_SU       = "su";
        } catch (Exception e) {
            return this;
        }
        bRunning = true;
        successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
        errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        os = new DataOutputStream(process.getOutputStream());

        try {
            //向sh写入要执行的命令
            os.write(command.getBytes());
            os.writeBytes("\n");
            os.flush();

            os.writeBytes("exit\n");
            os.flush();

            os.close();
            //如果等待时间设置为非正，就不开启超时关闭功能
            if (maxTime > 0) {
                //超时就关闭进程
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(maxTime);
                        } catch (Exception e) {
                        }
                        try {
                            int ret = process.exitValue();
                            LogUtils.i("auto", "exitValue Stream over"+ret);
                        } catch (IllegalThreadStateException e) {
                            LogUtils.i("auto", "take maxTime,forced to destroy process");
                            process.destroy();
                        }
                    }
                }).start();
            }

            //开一个线程来处理input流
            final Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    String line;
                    Lock writeLock = lock.writeLock();
                    try {
                        while ((line = successResult.readLine()) != null) {
                            line += "\n";
                            writeLock.lock();
                            result.append(line);
                            writeLock.unlock();
                        }
                    } catch (Exception e) {
                        LogUtils.i("auto", "read InputStream exception:" + e.toString());
                    } finally {
                        try {
                            successResult.close();
                            LogUtils.i("auto", "read InputStream over");
                        } catch (Exception e) {
                            LogUtils.i("auto", "close InputStream exception:" + e.toString());
                        }
                    }
                }
            });
            t1.start();

            //开一个线程来处理error流
            final Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    String line;
                    Lock writeLock = lock.writeLock();
                    try {
                        while ((line = errorResult.readLine()) != null) {
                            line += "\n";
                            writeLock.lock();
                            result.append(line);
                            writeLock.unlock();
                        }
                    } catch (Exception e) {
                        LogUtils.i("auto", "read ErrorStream exception:" + e.toString());
                    } finally {
                        try {
                            errorResult.close();
                            LogUtils.i("auto", "read ErrorStream over");
                        } catch (Exception e) {
                            LogUtils.i("auto", "read ErrorStream exception:" + e.toString());
                        }
                    }
                }
            });
            t2.start();

            Thread t3 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //等待执行完毕
                        t1.join();
                        t2.join();
                        process.waitFor();
                    } catch (Exception e) {

                    } finally {
                        bRunning = false;
                        LogUtils.i("auto", "run command process end");
                    }
                }
            });
            t3.start();

            if (bSynchronous) {
                LogUtils.i("auto", "run is go to end");
                t3.join();
                LogUtils.i("auto", "run is end");
            }
        } catch (Exception e) {
            LogUtils.i("auto", "run command process exception:" + e.toString());
        }
        return this;
    }

//    public static final String SDCARD_ROOT=Environment.getExternalStorageDirectory().getAbsolutePath();
//    public static final String AAA_PATH=SDCARD_ROOT+"/wifidog.conf";

    //读取目标文件（绝对路径）指定内容“#TrustedMACList ”的那一行
//    String cmd3="sed -n '/#TrustedMACList /,//p' "+AAA_PATH;
//    String str3 = new ExeCmdUtils().run(cmd3, 10000).getResult();
//    Log.i("auto", str3+"button3");
//    Toast.makeText(MainActivity.this, str3,Toast.LENGTH_SHORT).show();


//    ExeCmdUtils cmd = new ExeCmdUtils(false).run("your cmd", 60000);
//    while(cmd.isRunning())
//    {
//        try {
//            sleep(1000);
//        } catch (Exception e) {
//
//        }
//        String buf = cmd.getResult();
//        //do something
//    }

}
