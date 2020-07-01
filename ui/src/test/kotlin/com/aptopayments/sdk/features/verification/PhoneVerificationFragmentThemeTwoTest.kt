package com.aptopayments.sdk.features.verification

import com.aptopayments.mobile.data.user.Verification
import com.aptopayments.sdk.AndroidTest
import com.aptopayments.sdk.core.di.fragment.FragmentFactoryImpl
import com.aptopayments.sdk.core.platform.BaseFragment
import com.aptopayments.sdk.features.auth.verification.PhoneVerificationContract
import com.aptopayments.sdk.features.auth.verification.PhoneVerificationFragmentThemeTwo
import com.aptopayments.sdk.features.auth.verification.VerificationViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.mockito.Mock
import kotlin.test.assertEquals

class PhoneVerificationFragmentThemeTwoTest : AndroidTest() {
    // Collaborators
    @Mock
    private lateinit var viewModel: VerificationViewModel
    private val verification = Verification("", "phone")
    private lateinit var sut: PhoneVerificationFragmentThemeTwo

    @Before
    override fun setUp() {
        super.setUp()
        startKoin {
            modules(module {
                viewModel { viewModel }
            })
        }
        sut = PhoneVerificationFragmentThemeTwo.newInstance(verification)
        sut.verification = verification
    }

    @Test
    fun `on fragment presented call view model to fetch verification`() {
        // When
        sut.onPresented()

        // Then
        verify(viewModel, never()).restartVerification { result -> result.isLeft }
    }

    @Test
    fun `on back pressed notify delegate`() {
        // Given
        val delegate = mock<PhoneVerificationContract.Delegate>()
        sut.delegate = delegate

        // When
        sut.onBackPressed()

        // Then
        verify(delegate).onBackFromPhoneVerification()
    }

    @Test
    fun `phone verification fragment for theme2 return expected fragment and set TAG`() {
        // Given
        val sut = FragmentFactoryImpl()
        val tag = "SOME_TAG"

        // When
        val fragment = sut.phoneVerificationFragment(verification, tag)

        // Then
        assert(fragment is PhoneVerificationFragmentThemeTwo)
        assertEquals(tag, (fragment as BaseFragment).TAG)
    }
}