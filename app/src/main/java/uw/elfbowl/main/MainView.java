package uw.elfbowl.main;

import android.content.Context;
import android.view.View;


public class MainView extends View {

   /* List<Crystal> crystal_list;
    Timer timer;
    Context c;*/

    public MainView(final Context context) {
        super(context);

     /*   c = context;
        crystal_list = new ArrayList<Crystal>();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                tick();
            }
        }, 0, 40);*/
    }

  /*  @Override
    public void onDraw(Canvas canvas) {
        for (int i = 0; i < crystal_list.size(); i++)
            crystal_list.get(i).draw(canvas);
    }

    public void tick() {
        if (crystal_list.size() < 60)
            crystal_list.add(new Crystal(c, Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels));

        for (int i = 0; i < crystal_list.size(); i++) {
            crystal_list.get(i).update();
            if (crystal_list.get(i).finished)
                crystal_list.remove(i);
        }

        this.postInvalidate();
    }*/
}
