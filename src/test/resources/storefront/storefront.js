const products = [
  { id: "bottle", name: "Everyday Bottle", detail: "Insulated stainless steel · 500 ml", price: 28, icon: "◉", bg: "#e5eee4", ink: "#477452" },
  { id: "tote", name: "Market Tote", detail: "Recycled cotton · Natural", price: 18, icon: "▱", bg: "#f1e8dc", ink: "#96734a" },
  { id: "mug", name: "Morning Mug", detail: "Hand-finished stoneware · 350 ml", price: 24, icon: "☕", bg: "#ece4df", ink: "#89685a" },
  { id: "soap", name: "Cedar Hand Soap", detail: "Plant-based · 300 ml", price: 12, icon: "✿", bg: "#e6e9d8", ink: "#65734e" }
];
const cart = new Map();
const money = amount => `$${amount.toFixed(2)}`;

function renderProducts(filter = "") {
  const grid = document.querySelector("#product-grid");
  const matches = products.filter(product => `${product.name} ${product.detail}`.toLowerCase().includes(filter.toLowerCase()));
  grid.innerHTML = matches.map(product => `
    <article class="product-card" data-testid="product-${product.id}">
      <div class="product-art" style="--art-bg:${product.bg};--art-ink:${product.ink}" aria-hidden="true">${product.icon}</div>
      <div class="product-info">
        <h3>${product.name}</h3><p>${product.detail}</p>
        <div class="product-bottom"><span class="product-price">${money(product.price)}</span>
          <button class="add-button" type="button" data-testid="add-to-cart-${product.id}" data-product-id="${product.id}">Add to bag</button>
        </div>
      </div>
    </article>`).join("");
  grid.querySelectorAll("[data-product-id]").forEach(button => button.addEventListener("click", () => addToCart(button.dataset.productId)));
  document.querySelector("#notice").textContent = matches.length ? "" : "No products match your search.";
}

function addToCart(id) {
  cart.set(id, (cart.get(id) || 0) + 1);
  renderCart();
  const product = products.find(item => item.id === id);
  document.querySelector("#notice").textContent = `${product.name} added to your bag.`;
}

function renderCart() {
  const entries = [...cart.entries()];
  const quantity = entries.reduce((sum, [, count]) => sum + count, 0);
  const total = entries.reduce((sum, [id, count]) => sum + products.find(item => item.id === id).price * count, 0);
  document.querySelector("#cart-count").textContent = quantity;
  document.querySelector("#cart-total").textContent = money(total);
  document.querySelector("#cart-empty").hidden = entries.length > 0;
  document.querySelector("#cart-items").innerHTML = entries.map(([id, count]) => {
    const product = products.find(item => item.id === id);
    return `<li data-testid="cart-item-${id}"><span>${product.name} × ${count}</span><strong>${money(product.price * count)}</strong></li>`;
  }).join("");
}

document.querySelector("#search").addEventListener("input", event => renderProducts(event.target.value));
document.querySelector("#checkout-form").addEventListener("submit", event => {
  event.preventDefault();
  if (!cart.size) {
    document.querySelector("#checkout-message").textContent = "Add an item before placing your demo order.";
    return;
  }
  const name = document.querySelector("#customer-name").value.trim();
  document.querySelector("#checkout-message").textContent = `Thanks, ${name}! Your demo order is confirmed.`;
  cart.clear();
  renderCart();
});
renderProducts();
renderCart();
