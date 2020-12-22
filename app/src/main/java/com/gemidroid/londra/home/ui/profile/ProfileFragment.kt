package com.gemidroid.londra.home.ui.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.gemidroid.f3aleity.utils.Loading
import com.gemidroid.londra.R
import com.gemidroid.londra.forgotpassword.ui.ForgetPasswordActivity
import com.gemidroid.londra.home.ui.HomeActivity
import com.gemidroid.londra.home.ui.address.UpdateAddressActivity
import com.gemidroid.londra.home.ui.department.ProductsViewModel
import com.gemidroid.londra.home.ui.department.model.CatProducstRes
import com.gemidroid.londra.home.ui.profile.model.AddAddressResponse
import com.gemidroid.londra.login.ui.LoginActivity
import com.gemidroid.londra.login.ui.model.LoginRes
import com.gemidroid.londra.utils.Validator
import com.google.gson.JsonObject
import com.hbisoft.pickit.PickiT
import com.hbisoft.pickit.PickiTCallbacks
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.squareup.picasso.Picasso
import creativitysol.com.planstech.api.Retrofit
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_profile.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.File
import java.net.UnknownHostException

class ProfileFragment : Fragment(), PickiTCallbacks {

    var pickiT: PickiT? = null
    var body: MultipartBody.Part? = null
    private var isInfoShow = true
    private var isAddressesShow = true
    private var isFavShow = true
    private val loginRes by lazy {
        Paper.book().read("login", LoginRes())
    }
    private val viewModel by activityViewModels<ProfileViewModel>()
    private val proViewModel by activityViewModels<ProductsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as HomeActivity).loading!!.show()

        pickiT = PickiT(activity, this)



        viewModel.profile(
            "Bearer ${loginRes.data.accessToken}",
            "fleetcart_session=${Retrofit.cookieJar.cookies[0].value}"
        )

        Retrofit.cookieJar.cookies.forEach { c -> Log.e("cook", "${c.name} ${c.value}") }

        proViewModel.listFavs(
            "Bearer ${loginRes.data.accessToken}"
        )



        img_profile.setOnClickListener { checkPermission() }






        img_my_information.setOnClickListener {
            if (isInfoShow) {
                ctl_my_information.visibility = View.GONE
                img_my_information.setImageResource(R.drawable.ic_arrow_up)
                isInfoShow = false
            } else {
                ctl_my_information.visibility = View.VISIBLE
                img_my_information.setImageResource(R.drawable.ic_arrow_down)
                isInfoShow = true
            }
        }

        img_my_address.setOnClickListener {
            if (isAddressesShow) {
                rec_my_addresses.visibility = View.GONE
                img_my_address.setImageResource(R.drawable.ic_arrow_up)
                isAddressesShow = false
            } else {
                rec_my_addresses.visibility = View.VISIBLE
                img_my_address.setImageResource(R.drawable.ic_arrow_down)
                isAddressesShow = true
            }
        }

        img_my_favourites.setOnClickListener {
            if (isFavShow) {
                rec_my_favourites.visibility = View.GONE
                img_my_favourites.setImageResource(R.drawable.ic_arrow_up)
                isFavShow = false
            } else {
                rec_my_favourites.visibility = View.VISIBLE
                img_my_favourites.setImageResource(R.drawable.ic_arrow_down)
                isFavShow = true
            }
        }

        btn_add_address.setOnClickListener {
            val intent = Intent(requireActivity(), UpdateAddressActivity::class.java)
            startActivity(intent)
        }

        btn_save.setOnClickListener {
            if (validateInputs()) {
                (activity as HomeActivity).loading!!.show()
                viewModel.updateProfile("Bearer ${loginRes.data.accessToken}", JsonObject().apply {
                    addProperty("first_name", edt_profile_name.text.toString())
                    addProperty("last_name", edt_profile_name_last.text.toString())
                    addProperty("email", edt_profile_email.text.toString())
                    addProperty("mobile", edt_profile_phone.text.toString())
                    addProperty("country_code", "SA")
                })
            }

        }


        txt_logout.setOnClickListener {
            Paper.book().delete("login")
            Paper.book().delete("cart")
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
            requireActivity().finish()
        }


        proViewModel.favsResponse.observe(viewLifecycleOwner, Observer {
            if (it != null && it.success) {
                rec_my_favourites.apply {
                    adapter =
                        MyFavouritesAdapter().also { i -> i.setList(it.data.data as ArrayList<CatProducstRes.Data.Data>) }
                }

            }
        })

        proViewModel.favsError.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if (it is UnknownHostException)
                    Toast.makeText(activity, "no internet connection", Toast.LENGTH_SHORT).show()
                else if (it is HttpException) {
                    Log.e("eeee", it.response()!!.errorBody()!!.string())

                    Toast.makeText(
                        activity,
                        it.response()!!.message().toString(),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else
                    Toast.makeText(activity, "server response error", Toast.LENGTH_SHORT).show()
            }
        })
        setResponse()
        setError()
        setUpdateResponse()
        setProfileError()
        addressError()
        getAddressesResponse()
    }

    fun setResponse() = viewModel.getResponse.observe(viewLifecycleOwner, Observer {
        (activity as HomeActivity).loading!!.dismiss()
        if (it != null && it.success) {
            it.data.let { u ->
                edt_profile_name.setText(u.firstName)
                edt_profile_name_last.setText(u.lastName)
                edt_profile_email.setText(u.email)
                edt_profile_phone.setText(u.mobile)
                if (!u.avatar.isNullOrEmpty())
                    Picasso.get().load(u.avatar).placeholder(R.drawable.ic_profile)
                        .error(R.drawable.ic_profile)
                        .centerCrop().fit()
                        .into(img_profile)
            }

        }


    })

    fun setError() = viewModel.getError.observe(viewLifecycleOwner, Observer {
        (activity as HomeActivity).loading!!.dismiss()

        if (it != null) {
            if (it is UnknownHostException)
                Toast.makeText(activity, "no internet connection", Toast.LENGTH_SHORT).show()
            else if (it is HttpException) {
                Log.e("eeee", it.response()!!.errorBody()!!.string())

                //var coky = it.response()!!.headers().get("Set-Cookie")!!.split(";")[0]
                //Log.e("coky", coky)

               // Paper.book().write("coky", coky)

                Toast.makeText(activity, it.response()!!.message().toString(), Toast.LENGTH_SHORT)
                    .show()
            } else
                Toast.makeText(activity, "server response error", Toast.LENGTH_SHORT).show()
        }

    })


    fun getAddressesResponse() = viewModel.getAddressResponse.observe(viewLifecycleOwner, Observer {
        if (it != null && it.success) {
            it.data.let { u ->
                rec_my_addresses.apply {
                    adapter = MyAddressesAdapter {
                        val intent = Intent(requireActivity(), UpdateAddressActivity::class.java)
                        intent.putExtra("addressId", it)
                        startActivity(intent)
                    }.also { t -> t.setList(u as ArrayList<AddAddressResponse.Data>) }
                }
            }

        }


    })


    fun addressError() = viewModel.getAddressError.observe(viewLifecycleOwner, Observer {
        (activity as HomeActivity).loading!!.dismiss()

        if (it != null) {
            if (it is UnknownHostException)
                Toast.makeText(activity, "no internet connection", Toast.LENGTH_SHORT).show()
            else if (it is HttpException) {
                Log.e("eeee", it.response()!!.errorBody()!!.string())

                Toast.makeText(activity, it.response()!!.message().toString(), Toast.LENGTH_SHORT)
                    .show()
            } else
                Toast.makeText(activity, "server response error", Toast.LENGTH_SHORT).show()
        }

    })


    fun setUpdateResponse() = viewModel.getUpdateResponse.observe(viewLifecycleOwner, Observer {
        (activity as HomeActivity).loading!!.dismiss()
        if (it != null && it.success) {
            it.data.let { u ->
                edt_profile_name.setText(u.firstName)
                edt_profile_name_last.setText(u.lastName)
                edt_profile_email.setText(u.email)
                edt_profile_phone.setText(u.mobile)

            }

        }


    })

    fun setProfileError() = viewModel.getUpdateError.observe(viewLifecycleOwner, Observer {
        (activity as HomeActivity).loading!!.dismiss()

        if (it != null) {
            if (it is UnknownHostException)
                Toast.makeText(activity, "no internet connection", Toast.LENGTH_SHORT).show()
            else if (it is HttpException) {
                Log.e("eeee", it.response()!!.errorBody()!!.string())

                Toast.makeText(activity, it.response()!!.message().toString(), Toast.LENGTH_SHORT)
                    .show()
            } else
                Toast.makeText(activity, "server response error", Toast.LENGTH_SHORT).show()
        }

    })


    fun validateInputs(): Boolean {
        if (edt_profile_name.text.isEmpty()) {
            edt_profile_name.error = "enter first name"
            return false
        }

        if (edt_profile_name_last.text.isEmpty()) {
            edt_profile_name_last.error = "enter last name"
            return false
        }
        if (!Validator.isValidEmail(edt_profile_email.text)) {
            edt_profile_email.error = "enter mail"
            return false
        }
        if (!Validator.isValidPhone(edt_profile_phone.text)) {
            edt_profile_phone.error = "enter valid phone"
            return false
        }

        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 555) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {

                    val uri = data.data

                    Picasso.get().load(uri).placeholder(R.drawable.ic_profile)
                        .error(R.drawable.ic_profile)
                        .centerCrop().fit()
                        .into(img_profile)
                    pickiT!!.getPath(uri, Build.VERSION.SDK_INT)
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }

    override fun PickiTonStartListener() {}

    override fun PickiTonProgressUpdate(progress: Int) {}

    override fun PickiTonCompleteListener(
        path: String,
        wasDriveFile: Boolean,
        wasUnknownProvider: Boolean,
        wasSuccessful: Boolean,
        Reason: String?
    ) {
        val file = File(path)


        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        body = MultipartBody.Part.createFormData("avatar", file.name, requestFile)

        (activity as HomeActivity).loading!!.show()

        viewModel.updateAvatar("Bearer ${loginRes.data.accessToken}", body)


    }

    override fun onStart() {
        super.onStart()

        viewModel.getAddresses("Bearer ${loginRes.data.accessToken}")


    }

    fun checkPermission() {
        Dexter.withContext(activity)
            .withPermission(
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(permissionGrantedResponse: PermissionGrantedResponse) {
                    val intent = Intent()
                    intent.type = "image/*"
                    intent.action = Intent.ACTION_GET_CONTENT
                    startActivityForResult(
                        Intent.createChooser(intent, "Select Picture"),
                        555
                    )
                }

                override fun onPermissionDenied(permissionDeniedResponse: PermissionDeniedResponse) {}
                override fun onPermissionRationaleShouldBeShown(
                    permissionRequest: PermissionRequest,
                    permissionToken: PermissionToken
                ) {
                    permissionToken.continuePermissionRequest()
                }
            }).onSameThread().check()
    }
}