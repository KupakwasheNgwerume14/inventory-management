loadItems();

document.getElementById("itemForm")
    .addEventListener("submit", async function(e) {

        e.preventDefault();

        const item = {
            name: document.getElementById("name").value,
            sku: document.getElementById("sku").value,
            category: document.getElementById("category").value,
            quantity: parseInt(document.getElementById("quantity").value),
            unitPrice: parseFloat(document.getElementById("unitPrice").value),
            reorderLevel: parseInt(document.getElementById("reorderLevel").value)
        };

        await fetch("/api/item", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(item)
        });

        document.getElementById("itemForm").reset();

        loadItems();
    });

async function loadItems() {

    const response =
        await fetch("/api/item");

    const items =
        await response.json();

    let rows = "";

    items.forEach(item => {

        rows += `
            <tr>
                <td>${item.id}</td>
                <td>${item.name}</td>
                <td>${item.sku}</td>
                <td>${item.category}</td>
                <td>${item.quantity}</td>
                <td>${item.unitPrice}</td>
            </tr>
        `;
    });

    document.querySelector(
        "#inventoryTable tbody"
    ).innerHTML = rows;
}
