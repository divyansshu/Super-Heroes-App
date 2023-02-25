package com.example.superheroesapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.Spring.StiffnessVeryLow
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.superheroesapp.model.HeroesList
import com.example.superheroesapp.model.SuperHeroes
import com.example.superheroesapp.ui.theme.SuperHeroesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuperHeroesAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SuperHeroesLayout()
                }
            }
        }
    }
}


@Composable
fun SuperHeroesApp(heroes: SuperHeroes, modifier: Modifier = Modifier) {

    Card(
        elevation = 2.dp,
        modifier = modifier
    ) {

        Row(
            modifier = Modifier
                .padding(16.dp)
                .sizeIn(minHeight = 72.dp),
            horizontalArrangement = Arrangement.Center
        ) {

            Column(modifier = Modifier.weight(5f)) {
                Text(
                    text = stringResource(id = heroes.heroName),
                    style = MaterialTheme.typography.h3
                )
                Text(
                    text = stringResource(id = heroes.heroPower),
                    style = MaterialTheme.typography.body1
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                Image(
                    painter = painterResource(id = heroes.heroImage),
                    contentDescription = null,
                    alignment = Alignment.TopCenter,
                    contentScale = ContentScale.FillWidth
                )
            }
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SuperHeroesLayout() {

    val visibleState = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately.
            targetState = true
        }
    }

    // Fade in entry animation for the entire list
    AnimatedVisibility(
        visibleState = visibleState,
        enter = fadeIn(
            animationSpec = spring(dampingRatio = DampingRatioLowBouncy)
        ),
        exit = fadeOut()
    ) {

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                HeroesTopBar()
            }) {
            LazyColumn {
                itemsIndexed(HeroesList.heroes) { index, item ->
                    SuperHeroesApp(
                        item,
                        modifier = Modifier
                            .padding(
                                horizontal = 16.dp,
                                vertical = 8.dp
                                // Animate each list item to slide in vertically
                            )
                            .animateEnterExit(
                                enter = slideInVertically(
                                    animationSpec = spring(
                                        stiffness = StiffnessVeryLow,
                                        dampingRatio = DampingRatioLowBouncy
                                    ), initialOffsetY = { it * (index + 1) }
                                )
                            )
                    )
                }
            }
        }
    }
}

@Composable
fun HeroesTopBar() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.h1
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DarkThemePreview() {
    SuperHeroesAppTheme(darkTheme = true) {
        SuperHeroesLayout()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    SuperHeroesAppTheme(darkTheme = false) {
        SuperHeroesLayout()
    }
}