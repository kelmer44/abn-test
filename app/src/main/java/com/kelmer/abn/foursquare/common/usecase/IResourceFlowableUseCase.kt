package com.kelmer.abn.foursquare.common.usecase

import com.kelmer.abn.foursquare.common.resource.Resource

interface IResourceFlowableUseCase<T : Any, in Params> : IUseCase {
    fun execute(
        params: Params,
        onNext: ((Resource<T>) -> Unit)
    )
}