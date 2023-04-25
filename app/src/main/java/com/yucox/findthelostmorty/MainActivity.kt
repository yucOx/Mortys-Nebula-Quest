package com.yucox.findthelostmorty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.yucox.findthelostmorty.databinding.ActivityMainBinding
import java.util.Random


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var score = 0
    var highscore = 0
    var imageArray = ArrayList<ImageView>()
    var handler = Handler()
    var runnable = Runnable { }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        imageArray.add(binding.imageView2)
        imageArray.add(binding.imageView3)
        imageArray.add(binding.imageView4)
        imageArray.add(binding.imageView5)
        imageArray.add(binding.imageView6)
        imageArray.add(binding.imageView7)
        imageArray.add(binding.imageView8)
        imageArray.add(binding.imageView9)
        imageArray.add(binding.imageView10)

        var alert = AlertDialog.Builder(this)
        alert.setTitle("Aptal Rick'ler! Bu Evrene bu kadar Rick fazla demiştim..")
        alert.setMessage("Ricklerden biri bir portal açtı ve bütün Mortyleri bir Dünya'ya ışınladı.Ona Morty'leri bulmasına ve paket etmesine yardım edecek misin?")
        alert.setPositiveButton("Evet"){_,_ ->
            Toast.makeText(applicationContext, "Görev başlasın!", Toast.LENGTH_SHORT).show()
        }
        alert.setNegativeButton("Hayır"){_,_ ->
            Toast.makeText(applicationContext, "Rick seni manipüle etti. Artık yardım etmek zorundasın.", Toast.LENGTH_LONG).show()
        }
        alert.show()

    }
    fun hideimages(){
        runnable = object : Runnable {
            override fun run() {
                for(image in imageArray){
                    image.visibility = View.INVISIBLE
                }
                //Bu şekilde rastgele bir sayı oluşturabilirsin.
                val random = Random()
                val randomIndex = random.nextInt(9)
                imageArray[randomIndex].visibility = View.VISIBLE
                handler.postDelayed(runnable,450)
            }

        }
        handler.post(runnable)

    }

    fun increaseScore(view : View){
        score++
        binding.scoreText.text ="Skor: ${score.toString()}"
    }
    fun start(view : View){
        score = 0
        binding.scoreText.text = "Skor: 0"
        hideimages()
        object : CountDownTimer(15000,1000){
            override fun onTick(p0: Long) {

                binding.timeText.text = "Zaman: ${p0 / 1000}"
                binding.button.isEnabled = false
            }

            override fun onFinish() {
                binding.timeText.text = "Zaman doldu!"
                if(highscore <= score) {
                    highscore = score
                    binding.highscore.text = "Yüksek Skor: ${highscore}"
                }
                var alert = AlertDialog.Builder(this@MainActivity)
                alert.setMessage("Oyun Bitti. Skorun: ${score}")
                alert.setNegativeButton("Tamam"){_,_ ->
                    Toast.makeText(applicationContext, "Oyun Bitti!", Toast.LENGTH_SHORT).show()}
                    .show()
                //Ben burada restart için buton kullandım ve onu tekrar çağırdım
                //Fakat siz kendi appinizi yapmak isterseniz ve yeniden oynama
                //seçeneği eklemek isterseniz şunu da kullanabilirsiniz;
                //val intent = intent -> startActivity(intent)
                //startActivity öncesi, finish() eklerseniz daha stabil olacaktır.
                score = 0
                binding.scoreText.text = "Skor: ${score}"
                binding.button.isEnabled = true
                handler.removeCallbacks(runnable)


            }
        }.start()
    }
}