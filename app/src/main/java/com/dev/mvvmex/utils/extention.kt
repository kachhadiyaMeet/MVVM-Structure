package com.dev.mvvmex.utils

import android.annotation.SuppressLint
import android.app.*
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.view.animation.LinearInterpolator
import android.view.animation.ScaleAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import com.dev.mvvmex.R
import com.google.android.material.snackbar.Snackbar
import com.tapadoo.alerter.Alerter
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.DecimalFormat
import java.util.*


fun Context.datePickerDialog(selectedDate: TextView, context: Context, isShowPast: Boolean) {
    val c: Calendar = Calendar.getInstance()
    var mYear = c.get(Calendar.YEAR)
    var mMonth = c.get(Calendar.MONTH)
    var mDay = c.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context/*, R.style.datePickerDialog*/,
        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            selectedDate.text =
                dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year
        },
        mYear,
        mMonth,
        mDay
    )

    if (!isShowPast) {
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000;
    }

    datePickerDialog.show()
}


fun Context.getAge(year: Int, month: Int, day: Int): String? {
    val dob = Calendar.getInstance()
    val today = Calendar.getInstance()
    dob[year, month] = day
    var age = today[Calendar.YEAR] - dob[Calendar.YEAR]
    if (today[Calendar.DAY_OF_YEAR] < dob[Calendar.DAY_OF_YEAR]) {
        age--
    }
    val ageInt = age
    return ageInt.toString()
}


fun Context.timePickerDialog(tvTime: TextView) {
    val mcurrentTime = Calendar.getInstance()
    val hour = mcurrentTime[Calendar.HOUR_OF_DAY]
    val minute = mcurrentTime[Calendar.MINUTE]
    val mTimePicker: TimePickerDialog = TimePickerDialog(
        this@timePickerDialog,
        { timePicker, selectedHour, selectedMinute ->
            tvTime.setText("$selectedHour:$selectedMinute")
        },
        hour,
        minute,
        true
    ) //Yes 24 hour time

//    mTimePicker.setTitle("Select Time")
    mTimePicker.show()
}

inline val View.show: View
    get() {
        if (visibility != View.VISIBLE) {
            visibility = View.VISIBLE
        }
        return this
    }

/**
 * Hide the view. (visibility = View.INVISIBLE)
 */
inline val View.hide: View
    get() {
        if (visibility != View.INVISIBLE) {
            visibility = View.INVISIBLE
        }
        return this
    }

/**
 * Remove the view (visibility = View.GONE)
 */

inline val View.remove: View
    get() {
        if (visibility != View.GONE) {
            visibility = View.GONE
        }
        return this
    }

/**
 * Remove the view (View.setEnable(true))
 */

inline val View.enable: View
    get() {
        isEnabled = true
        return this
    }

/**
 * Remove the view (View.setEnable(false))
 */
inline val View.disable: View
    get() {
        isEnabled = false
        return this
    }

inline val cleanGC: Unit
    get() {

        System.runFinalization()
        Runtime.getRuntime().gc()
        Runtime.getRuntime().freeMemory()
        System.gc()
    }

fun View.showSnackBar(@NonNull fMessage: String) {
    Snackbar
        .make(this, fMessage, Snackbar.LENGTH_LONG)
        .show()
}

fun Context.showAlert(mActivity: Activity, msg: String, type: Int) {
    val color = if (type == 1) {
        R.color.green_100
    } else {
        R.color.red
    }
    Alerter.create(mActivity)
        .setTitle(resources.getString(R.string.app_name))
        .setText(msg)
        .setIcon(R.mipmap.ic_launcher)
        .setBackgroundColorRes(color)
        .show()
}

fun Context.toast(@NonNull fMessage: String) {
    Toast.makeText(this, fMessage, Toast.LENGTH_SHORT).show()
}

fun Context.toastLong(@NonNull fMessage: String) {
    Toast.makeText(this, fMessage, Toast.LENGTH_LONG).show()
}

fun Context.hideStatusbar(mActivity: Activity) {
    mActivity.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
}

fun Context.bounceAnimation(v: View) {
    val anim_image_add = ScaleAnimation(1.0f, 0.9f, 1.0f, 0.9f, 1, 0.5f, 1, 0.5f)
    anim_image_add.setDuration(500L)
    anim_image_add.setInterpolator(LinearInterpolator())
    anim_image_add.setRepeatCount(-1)
    anim_image_add.setRepeatMode(2)
    v.startAnimation(anim_image_add)
}

inline val Context.isOnline: Boolean
    @SuppressLint("MissingPermission")
    get() {

        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

inline val Activity.getProgressDialog: ProgressDialog
    get() {
        val mProgressDialog = ProgressDialog(this, R.style.AppCompatAlertDialogStyle)
        mProgressDialog.setCancelable(false)
        mProgressDialog.setMessage(getString(R.string.loading))
        return mProgressDialog
    }

inline val Dialog.hideDialog: Unit
    get() {
        if (this.isShowing) {
            this.dismiss()
            this.cancel()
        }
    }

fun <T> Activity.goTo(
    fNextActivityClass: Class<T>,
    IsNeedToFinish: Boolean = false,
    fEnterAnimId: Int = R.anim.right_in,
    fExitAnimId: Int = R.anim.left_out,
    fBundle: Bundle.() -> Unit = {}
) {
    val lIntent = Intent(this, fNextActivityClass)
    lIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
    lIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    lIntent.putExtras(Bundle().apply(fBundle))
    this.startActivity(lIntent)
    this.overridePendingTransition(fEnterAnimId, fExitAnimId)

    if (IsNeedToFinish) {
        this.finish()
    }
}

fun Context.isValidEmail(target: CharSequence?): Boolean {
    return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
}

fun Context.isPermissionGranted(@NonNull fPermissionList: List<String>): Boolean {

    var lIsGranted = true
    for (lPermission in fPermissionList) {
        if (ActivityCompat.checkSelfPermission(
                this,
                lPermission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            lIsGranted = false
        }
    }

    return lIsGranted
}

fun Context.doNotAskAgain(fMessage: String) {
    val builder = AlertDialog.Builder(this)

    builder.setTitle("Permissions Required")
    builder.setMessage("Please allow permissions for $fMessage.")

    builder.setPositiveButton(android.R.string.ok) { dialog, _ ->
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.data = Uri.parse("package:" + this.packageName)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        this.startActivity(intent)
        dialog.dismiss()
    }
    builder.setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.dismiss() }
    builder.create().show()
}

inline val String.capitalize: String
    get() {
        val strLen = this.length
        if (strLen == 0) {
            return this
        }
        val firstCodePoint = this.codePointAt(0)
        val newCodePoint = Character.toTitleCase(firstCodePoint)
        if (firstCodePoint == newCodePoint) {
            return this
        }
        val newCodePoints = IntArray(strLen) // cannot be longer than the char array
        var outOffset = 0
        newCodePoints[outOffset++] = newCodePoint // copy the first code point
        var inOffset = Character.charCount(firstCodePoint)
        while (inOffset < strLen) {
            val codePoint = this.codePointAt(inOffset)
            newCodePoints[outOffset++] = codePoint // copy the remaining ones
            inOffset += Character.charCount(codePoint)
        }
        return String(newCodePoints, 0, outOffset)
    }

inline val Activity.getOutputMediaFile: File?
    get() {

        Log.e("mTag", ": ${this.getExternalFilesDir(Environment.DIRECTORY_DCIM)}")

        val mediaStorageDir = File(
            this.getExternalFilesDir(Environment.DIRECTORY_DCIM)!!.path
                    + File.separator
                    + "Camera"
        )
        Log.e("mTag", "getOutputMediaFile: ${mediaStorageDir.path}")

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null
            }
        }

        val mImageName = "IMG_" + System.currentTimeMillis() + ".jpg"


        return File(mediaStorageDir.path + File.separator + mImageName)
    }

fun Context.encodeTobase64(image: Bitmap): String {

    val baos = ByteArrayOutputStream()
    image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val b = baos.toByteArray()
    val imageEncoded = Base64.encodeToString(b, Base64.DEFAULT)
    return "data:image/png;base64,$imageEncoded"
}

private val suffix = arrayOf("", "k", "m", "b", "t")

fun Context.coolFormat(n: Double): String? {
    var r: String = DecimalFormat("##0E0").format(n)
    r = r.replace("E[0-9]".toRegex(), suffix.get(Character.getNumericValue(r[r.length - 1]) / 3))
    while (r.length > 4 || r.matches("[0-9]+\\.[a-z]".toRegex())) {
        r = r.substring(0, r.length - 2) + r.substring(r.length - 1)
    }
    return r
}

inline val View.hideKeyBord: Unit
    get() {
        this.clearFocus()
        (this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(this.windowToken, 0)
    }

/**
 * Extension method for Showing Key Bord
 */
inline val View.showKeyBord: Unit
    get() {
        this.requestFocus()
        (this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .showSoftInput(this, 0)
    }

/**
 * Extension method for Focus EditText or showing keyBord
 */
inline val EditText.setKeyBordFocus: Unit
    get() {
        this.requestFocus()
        this.showKeyBord
    }

