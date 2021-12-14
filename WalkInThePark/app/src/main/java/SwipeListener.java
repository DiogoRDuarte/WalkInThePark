import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class SwipeListener implements View.OnTouchListener {
    private final GestureDetector gestureDetector;

    public SwipeListener (Context context){
        this.gestureDetector = new GestureDetector(context,new GestureListener());
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_LIMIT = 100;
        private static final int SWIPE_LIMIT_SPEED = 100;

        //@Override
        public boolean OnDown(MotionEvent e){
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_LIMIT && Math.abs(velocityX) > SWIPE_LIMIT_SPEED) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                        result = true;
                    }
                }
                else if (Math.abs(diffY) > SWIPE_LIMIT && Math.abs(velocityY) > SWIPE_LIMIT_SPEED) {
                    if (diffY > 0) {
                        onSwipeBottom();
                    } else {
                        onSwipeTop();
                    }
                    result = true;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }

    }

    private void onSwipeBottom() {
    }

    private void onSwipeLeft() {
    }

    private void onSwipeRight() {
    }

    private void onSwipeTop() {
    }
}
