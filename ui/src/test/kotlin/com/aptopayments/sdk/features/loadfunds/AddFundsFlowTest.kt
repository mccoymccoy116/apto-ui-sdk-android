package com.aptopayments.sdk.features.loadfunds

import com.aptopayments.mobile.data.payment.Payment
import com.aptopayments.sdk.UnitTest
import com.aptopayments.sdk.core.di.fragment.FragmentFactory
import com.aptopayments.sdk.features.loadfunds.result.AddFundsResultFragment
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module

private const val CARD_ID = "id_1234"

class AddFundsFlowTest : UnitTest() {
    private val mockFragmentFactory: FragmentFactory = mock()

    private lateinit var sut: AddFundsFlow

    @Before
    fun setUp() {
        startKoin {
            modules(
                module {
                    single { mockFragmentFactory }
                }
            )
        }
    }

    @Test
    fun `given cardId and Payment when onPaymentResult then fragmentFactory is called correctly`() {
        sut = AddFundsFlow(CARD_ID, onClose = {})
        val payment: Payment = mock()
        val tag = "AddFundsResultFragment"
        val fragment = mock<AddFundsResultFragment>() { on { TAG } doReturn tag }
        given {
            mockFragmentFactory.addFundsResultFragment(
                cardId = CARD_ID,
                payment = payment,
                tag = tag
            )
        }.willReturn(fragment)

        sut.onPaymentResult(payment)

        verify(mockFragmentFactory).addFundsResultFragment(CARD_ID, payment, tag)
    }
}
