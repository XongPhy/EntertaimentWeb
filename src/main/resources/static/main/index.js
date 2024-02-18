window.addEventListener('scroll', function () {
  var header = document.querySelector('header');
  header.classList.toggle('sticky', window.scrollY > 0);
});

const searchInput = document.querySelector('#keyword');
const cards = document.querySelectorAll('.card');
const searchForm = document.querySelector('form');

searchInput.addEventListener('input', function () {
  const searchTerm = this.value.toLowerCase();

  cards.forEach(function (card) {
    const title = card.querySelector('h4').textContent.toLowerCase();

    if (title.includes(searchTerm)) {
      card.style.display = 'block';
    } else {
      card.style.display = 'none';
    }
  });
});

searchForm.addEventListener('submit', function (event) {
  event.preventDefault(); // Prevents the default form submission

  // Retrieve the value from the form input
  const searchTerm = searchInput.value.toLowerCase();

  // Perform the search logic similar to the input event
  cards.forEach(function (card) {
    const title = card.querySelector('h4').textContent.toLowerCase();

    if (title.includes(searchTerm)) {
      card.style.display = 'block';

      // Scroll to the card element
      card.scrollIntoView({ behavior: 'smooth', block: 'start' });
    } else {
      card.style.display = 'none';
    }
  });

  // You can also submit the form here if needed
  // this.submit();
});