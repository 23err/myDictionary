package com.example.mydictionary

import com.example.mydictionary.domain.Card
import com.example.mydictionary.domain.interfaces.IRVPresenter
import com.example.mydictionary.domain.interfaces.IScreens
import com.example.mydictionary.domain.interfaces.IWordView
import com.example.mydictionary.domain.interfaces.`CardsView$$State`
import com.example.mydictionary.interactors.RepositoryInteractor
import com.example.mydictionary.viewmodels.CardsPresenter
import com.github.terrakok.cicerone.Router
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


class CardsPresenterTest {

    @Mock
    private lateinit var wordsPresenter: IRVPresenter<Card, IWordView>

    @Mock
    private lateinit var router: Router

    @Mock
    private lateinit var screens: IScreens

    @Mock
    private lateinit var repositoryInteractor: RepositoryInteractor

    @Mock
    private lateinit var viewState: `CardsView$$State`

    private lateinit var cardsPresenter: CardsPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        cardsPresenter = CardsPresenter(
            repositoryInteractor,
            wordsPresenter,
            router,
            screens
        )
        cardsPresenter.setViewState(viewState)
    }

    @Test
    fun addWordClick_Test() {
        cardsPresenter.addWordClick()
        verify(router, times(1)).navigateTo(screens.addWord())
    }

    @Test
    fun showError_Test(){
        val throwable = Throwable("test")
        cardsPresenter.handleError(throwable)
        verify(viewState, times(1)).showError("test")
    }

}