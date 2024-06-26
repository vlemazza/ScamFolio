package com.dosti.scamfolio.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.dosti.scamfolio.R
import com.dosti.scamfolio.db.entities.CoinModelAPIDB
import com.dosti.scamfolio.viewModel.SearchedCryptosViewModel

@Composable
fun SearchScreen(
    searchQuery: String,
    searchResults: List<CoinModelAPIDB>,
    onSearchQueryChange: (String) -> Unit,
    selectedCoin: (String) -> Unit,
    onToggleShowWallet: () -> Unit,
    showWallet: Boolean
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, top = 10.dp, bottom = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
         TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp, start = 8.dp),
             value = searchQuery,
             singleLine = true,
             keyboardOptions = KeyboardOptions.Default.copy(
                 imeAction = ImeAction.Done,
                 autoCorrect = false,
                 capitalization = KeyboardCapitalization.None
             ),
             keyboardActions = KeyboardActions(
                 onDone = {keyboardController?.hide()}
             ),
             onValueChange = onSearchQueryChange,
             label = { Text(text = stringResource(R.string.search), color = Color.LightGray, fontStyle = FontStyle.Italic) },
             colors = OutlinedTextFieldDefaults.colors(
                 focusedTextColor = Color.White,
                 unfocusedTextColor = Color.White,
                 focusedBorderColor = Color.Transparent,
                 focusedLeadingIconColor = Color.White,
                 unfocusedLeadingIconColor = Color.White,
                 unfocusedBorderColor = Color.Transparent,
                 unfocusedLabelColor = Color.Transparent,
                 focusedContainerColor = Color.DarkGray,
                 unfocusedContainerColor = Color.DarkGray
                 ),
             leadingIcon = { Icon(Icons.Default.Search, contentDescription = "") },
             shape = MaterialTheme.shapes.large,
             )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start
            ) {
                Button(
                    onClick = { onToggleShowWallet() },
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp, top = 4.dp),
                    enabled = showWallet,
                    colors =  ButtonDefaults.buttonColors(
                        disabledContainerColor = MaterialTheme.colorScheme.secondary,
                        disabledContentColor = Color.White,
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = Color.LightGray
                        )
                ) {
                    Text(
                        text = stringResource(R.string.allcoinsearch),
                    )
                }

                Button(
                    onClick = { onToggleShowWallet() },
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp, top = 4.dp),
                    enabled = !showWallet,
                    colors =  ButtonDefaults.buttonColors(
                        disabledContainerColor = MaterialTheme.colorScheme.secondary,
                        disabledContentColor = Color.White,
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = Color.LightGray
                    )

                ) {
                    Text(
                        text = stringResource(R.string.inwalletcoinsearch),
                    )
                }
            }
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                items = searchResults,
                key = { coin ->
                    coin.id
                }
            ) { coin ->
                CoinItem(coin = coin, selectedCoin)
            }

            if(searchResults.isEmpty()){
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = stringResource(R.string.walletempty),
                            textAlign = TextAlign.Center,
                            color = Color.LightGray,
                            fontStyle = FontStyle.Italic
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun CoinItem(coin: CoinModelAPIDB, selectedCoin: (String) -> Unit) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable(onClick = { selectedCoin(coin.id) }),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = rememberAsyncImagePainter(coin.image, placeholder  = painterResource(R.drawable.placeholderimage), fallback = painterResource(R.drawable.placeholderimage), error = painterResource(R.drawable.placeholderimage)),
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = coin.name,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Card(
                    shape = MaterialTheme.shapes.extraSmall,
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Gray,
                        contentColor = Color.White
                    )
                ) {
                }
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = coin.symbol,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

        }

        Column(
            modifier = Modifier.width(IntrinsicSize.Max),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {

            Text(
                modifier = Modifier
                    .width(IntrinsicSize.Max),
                text = coin.current_price + "€",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                maxLines = 1
            )

            Spacer(modifier = Modifier.padding(1.dp))

            Card(
                modifier = Modifier
                    .requiredWidth(72.dp),
                shape = MaterialTheme.shapes.small,
                colors = CardDefaults.cardColors(
                    containerColor = changePercentColor(coin.price_change_percentage_24h),
                    contentColor = changePercentColor(coin.price_change_percentage_24h)
                )
            ) {
                Text(
                    text = textPriceChange(coin.price_change_percentage_24h),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,

                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 1.dp)
                        .align(Alignment.End),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
fun SearchedCryptos(
    viewModel: SearchedCryptosViewModel,
    selectedCoin: (String) -> Unit
) {
    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle(lifecycleOwner = androidx.compose.ui.platform.LocalLifecycleOwner.current)

    searchResults?.let { it ->
        SearchScreen(
            searchQuery = viewModel.searchQuery,
            searchResults = it,
            onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
            selectedCoin,
            onToggleShowWallet = { viewModel.onToggleShowWallet() },
            showWallet = viewModel.showWalletOnly
        )
    }
}