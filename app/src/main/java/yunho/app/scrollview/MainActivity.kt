package yunho.app.scrollview

import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.WindowCompat
import yunho.app.scrollview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var anion = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))

        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbar.title = title
        binding.scrollView.viewTreeObserver.addOnScrollChangedListener {
            val scrollVal = binding.scrollView.scrollY

            //600dp가 지나면  애니메이션 실행
            if(scrollVal > 600f.dpToPx(this).toInt()){

                if(anion.not()){
                    binding.constraintLayout.transitionToEnd()
                }
            }else{
                if(anion.not()){
                    binding.constraintLayout.transitionToStart()
                }
            }
        }
        //애니메이션을 연속으로 하지않도록 설정
        binding.constraintLayout.setTransitionListener(object : MotionLayout.TransitionListener{
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {
                anion = true
            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {

            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                anion = false
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {

            }

        })
    }
    fun Float.dpToPx(context: Context): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, context.resources.displayMetrics)

    fun Activity.setStatusBarTransparent() {
        window?.apply {
            setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        if(Build.VERSION.SDK_INT >= 30) {	// API 30 에 적용
            WindowCompat.setDecorFitsSystemWindows(window, false)
        }
    }

}