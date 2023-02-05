package com.kalex.dogescollection.authentication.epoxy

import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.kalex.dogescollection.R
import com.kalex.dogescollection.common.epoxyhelpers.KotlinEpoxyHolder

@EpoxyModelClass(layout = R.layout.authentication_input_field)
abstract class EpoxyInputFieldModel  : EpoxyModelWithHolder<EpoxyInputFieldModel.Holder>() {




    inner class Holder():  KotlinEpoxyHolder(){

    }
}