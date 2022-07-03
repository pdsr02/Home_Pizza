package es.ulpgc.eite.homepizza.cart;

import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.List;

import es.ulpgc.eite.homepizza.app.AppMediator;
import es.ulpgc.eite.homepizza.app.CartToAmountState;
import es.ulpgc.eite.homepizza.app.StoreToCartState;
import es.ulpgc.eite.homepizza.data.CartItem;

/**
 * Created by Luis on mayo, 2022
 */
public class CartPresenter implements CartContract.Presenter {

  public static String TAG = "HomePizza.CartPresenter";

  private WeakReference<CartContract.View> view;
  private CartState state;
  private CartContract.Model model;
  private AppMediator mediator;

  public CartPresenter(AppMediator mediator) {
    this.mediator = mediator;
  }

  @Override
  public void onStart() {
    Log.e(TAG, "onStart()");
    // TODO: include some code if is necessary
    state= mediator.getCartScreenState();
    state.items = model.getStoredData();
  }

  @Override
  public void onRestart() {
    Log.e(TAG, "onRestart()");
    // TODO: include some code if is necessary
   state= mediator.getCartScreenState();
  }

  @Override
  public void onResume() {
    Log.e(TAG, "onResume()");
    // TODO: include some code if is necessary
    StoreToCartState storeItem = mediator.getStoreToCartScreenState();
    if (storeItem != null) {
      CartItem cartItem = new CartItem(storeItem.item);
      if(pizzaExist(cartItem)==false) {
        state.items.add(cartItem);
       }
    }
    view.get().onViewModelDataUpdated(state);
  }

  public boolean pizzaExist(CartItem item){
    List<CartItem> items = state.items;
    for(int i=0;i<items.size();i++) {
      if (items.get(i).name==item.name) {
        items.get(i).amount++;
        return true;
      }
    }
    return false;
  }

  @Override
  public void onBackPressed() {
    Log.e(TAG, "onBackPressed()");

    // TODO: include some code if is necessary
  }

  @Override
  public void onPause() {
    Log.e(TAG, "onPause()");

    // TODO: include some code if is necessary
  }

  @Override
  public void onDestroy() {
    Log.e(TAG, "onDestroy()");

    // TODO: include some code if is necessary
  }

  @Override
  public void onButtonClicked() {
    Log.e(TAG, "onButtonClicked()");
    // TODO: include some code if is necessary
    view.get().navigateToStoreScreen();
  }

  @Override
  public void onItemClicked(Integer position) {
    Log.e(TAG, "onItemClicked()");
    // TODO: include some code if is necessary
    CartToAmountState newState = new CartToAmountState();
    CartItem cartItem = state.items.get(position);
    newState.item= cartItem;
    newState.subtotal= cartItem.price;
    newState.total = cartItem.price * cartItem.amount;
    mediator.setCartToAmountScreenState(newState);
    view.get().navigateToAmountScreen();
  }


  @Override
  public void injectView(WeakReference<CartContract.View> view) {
    this.view = view;
  }

  @Override
  public void injectModel(CartContract.Model model) {
    this.model = model;
  }

}