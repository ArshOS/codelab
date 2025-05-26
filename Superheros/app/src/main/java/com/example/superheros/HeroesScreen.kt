package com.example.superheros

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.superheros.model.Hero
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.superheros.model.HeroesRepository
import com.example.superheros.ui.theme.Shape
import com.example.superheros.ui.theme.SuperheroesTheme

@Composable
fun HeroesList(
    heroes: List<Hero>, modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {

    val visibleState = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately.
            targetState = true
        }
    }

    //Comment all Animation related code to see the preview

    AnimatedVisibility(
        visibleState = visibleState,
        enter = fadeIn(
            animationSpec = spring(dampingRatio = DampingRatioLowBouncy)
        ),
        exit = fadeOut(),
        modifier = modifier
    )
    {
        LazyColumn(contentPadding = contentPadding) {
            itemsIndexed(heroes) { index, hero ->
                HeroListItem(
                    hero = hero,
                    modifier = modifier.padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    )
                )
            }
        }
    }

}

@Composable
fun HeroListItem(modifier: Modifier = Modifier, hero: Hero) {
    Card(
        elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.card_elevation)),
        modifier = modifier,
        shape = Shape.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Row(
            modifier
                .fillMaxWidth()
                .padding(16.dp)
                .sizeIn(minHeight = 72.dp)
        ) {
            Column(
                modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(hero.nameRes),
                    style = MaterialTheme.typography.displaySmall,
                )
                Text(
                    text = stringResource(hero.descriptionRes),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
            Spacer(modifier.width(dimensionResource(R.dimen.image_text_space)))
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(8.dp))

            ) {
                Image(
                    painter = painterResource(hero.imageRes),
                    contentDescription = null,
                    alignment = Alignment.TopCenter,
                    contentScale = ContentScale.FillWidth
                )
            }
        }
    }

}

@Preview
@Composable
fun HeroItemPreview() {
    SuperheroesTheme {
        HeroListItem(
            modifier = Modifier, Hero(
                nameRes = R.string.hero1,
                descriptionRes = R.string.description1,
                imageRes = R.drawable.android_superhero1,
            )
        )
    }
}

@Preview
@Composable
fun HeroListPreview() {
    SuperheroesTheme {
        HeroesList(
            heroes = HeroesRepository.heroes
        )
    }
}