package com.kalex.dogescollection.authentication.epoxy

import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.kalex.dogescollection.R
import com.kalex.dogescollection.common.epoxyhelpers.KotlinEpoxyHolder

@EpoxyModelClass(layout = R.layout.authentication_input_password_field)
abstract class EpoxyInputPasswordModel : EpoxyModelWithHolder<EpoxyInputPasswordModel.Holder>() {

    inner class Holder():  KotlinEpoxyHolder(){

    }
}