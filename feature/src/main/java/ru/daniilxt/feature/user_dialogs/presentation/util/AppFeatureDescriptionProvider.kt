package ru.daniilxt.feature.welcome_screen_description.presentation.util

import android.content.Context
import androidx.appcompat.content.res.AppCompatResources
import ru.daniilxt.feature.R
import ru.daniilxt.feature.domain.model.FeatureDescription

object AppFeatureDescriptionProvider {
    fun getFeatureDescription(context: Context) = listOf(
        FeatureDescription(
            1, "Случайный режим", "Получайте случайного собеседника, чтобы начать общение",
            AppCompatResources.getDrawable(context, R.drawable.ic_dice_30)
        ),
        FeatureDescription(
            2,
            "Собеседники вокруг ВУЗа",
            "Выберите своего собеседника на карте ВУЗов, и начнимте общение уже сейчас",
            AppCompatResources.getDrawable(context, R.drawable.ic_map_30)
        ),
        FeatureDescription(
            3, "Простой список", "Выбирайте сами из списка предложенных карточек собеседников",
            AppCompatResources.getDrawable(context, R.drawable.ic_card_avatar_30)
        ),
    )
}
