package com.kalex.dogescollection.authentication.epoxy

import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.kalex.dogescollection.R
import com.kalex.dogescollection.common.epoxyhelpers.KotlinEpoxyHolder
import com.kalex.dogescollection.dogList.presentation.epoxy.DogDetailInfoModel

@EpoxyModelClass()
abstract class EpoxyImputField  : EpoxyModelWithHolder<EpoxyImputField.Holder>() {




    inner class Holder():  KotlinEpoxyHolder(){

    }
}