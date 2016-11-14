package gr.iserm.java.web.servlet;

import rx.Observable;
import rx.schedulers.Schedulers;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static gr.iserm.java.web.util.Utils.sleep;

@WebServlet(urlPatterns = "/reactive", asyncSupported = true)
public class ReactiveServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long start_tstamp = System.currentTimeMillis();
        System.out.println("async servlet request received by thread-"+Thread.currentThread().getId());

        final AsyncContext asyncContext = req.startAsync();
        executeAsynchronously(asyncContext)
                .subscribeOn(Schedulers.io()) // io-bound operations with be performed by a thread-pool
                .subscribe((o) -> {
                    long now = System.currentTimeMillis();
                    System.out.println("async servlet request processed by thread-"+Thread.currentThread().getId()+" in ["+(now-start_tstamp)+"]ms");
                    asyncContext.complete();
                });

        long end_tstamp = System.currentTimeMillis();
        System.out.println("servlet execution took ["+(end_tstamp-start_tstamp)+"]ms");
    }

    private Observable<Void> executeAsynchronously(AsyncContext asyncContext) {
        return Observable.fromCallable(() -> {
            try {
                sleep(10);
                asyncContext.getResponse().getWriter().write("Reactive async response!");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                return null;
            }
        });
    }
}
