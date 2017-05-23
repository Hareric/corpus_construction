import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


class IPHandleTask implements Runnable{
    private String ip;

    public IPHandleTask(String ip){
        this.ip = ip;
    }

    @Override
    public void run() {
        //TODO 处理该IP信息
        System.out.println(Thread.currentThread().getName()+"正处理ip:"+ip);
    }

}


public class MainController {
    private ExecutorService executor = Executors.newFixedThreadPool(3);
    private List<String> datas ;

    public MainController(List<String> datas ){
        this.datas = datas;
    }

    public void submitTask(){
        if(datas==null||datas.isEmpty()){
            System.out.println("no data to process.");
            return;
        }

        for(String data:datas){
            executor.submit(new IPHandleTask(data));
        }

        //处理完成后关闭线程池
        executor.shutdown();
    }

    public static void main(String[] args) {
        String[] s = {"1","2","3","4","5","6","7","8","9","10"};
        List<String> datas= Arrays.asList(s);
        MainController c = new MainController(datas);
        c.submitTask();
    }
}
