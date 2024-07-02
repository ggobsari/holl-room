document.addEventListener('DOMContentLoaded', function () {
    //글쓰기 버튼 활성화
    document.getElementById('write-button').addEventListener('click', function () {
        window.location.href = "http://localhost:8090/hollroom/mypage/inquiry_write";
    })
    //==============================================================================================================
    let category = 'all';
    let currentPage = 0;

    const postList = document.getElementById('inquiry-table-body');
    const pagination = document.querySelector('.my-paging');

    loadInquiries(category, currentPage);

    function loadInquiries(category, page) {
        fetch(`inquiryposts?category=${category}&page=${page}`)
            .then(response => response.json())
            .then(data => {
                if (Array.isArray(data.posts)) {
                    renderInquiries(data.posts);
                    renderPagination(data.totalPages, page);
                } else {
                    console.error('Error: Expected array but received:', data.posts);
                }
            })
            .catch(error => console.error('Error fetching inquiries:', error));
    }

    function renderInquiries(posts) {
        postList.innerHTML = posts.map(inquiry => `
            <tr>
                <td>${inquiry.postId}</td>
                <td><a href="/hollroom/mypage/inquiry/${inquiry.postId}">${inquiry.title}</a></td>
                <td>${inquiry.createdAt.split(' ')[0]}</td>
                <td>${inquiry.answered ? '예' : ''}</td>
                <td>${inquiry.answeredAt ? inquiry.answeredAt.split(' ')[0] : ''}</td>
            </tr>
        `).join('');
    }

    function renderPagination(totalPages, currentPage) {
        pagination.innerHTML = '';

        if (totalPages > 1) {
            if (currentPage > 3) {
                const prevLink = document.createElement('a');
                prevLink.href = '#';
                prevLink.innerHTML = '&#5130;';
                prevLink.addEventListener('click', function (event) {
                    event.preventDefault();
                    loadInquiries(category, currentPage - 1);
                });
                pagination.appendChild(prevLink);
            }

            const pages = Array.from({ length: totalPages }, (_, i) => `
                <a href="#" class="${i === currentPage ? 'active' : ''}">${i + 1}</a>
            `).join('');

            pagination.innerHTML += pages;

            if (totalPages > 3) {
                const nextLink = document.createElement('a');
                nextLink.href = '#';
                nextLink.innerHTML = '&#5125;';
                nextLink.addEventListener('click', function (event) {
                    event.preventDefault();
                    loadInquiries(category, currentPage + 1);
                });
                pagination.appendChild(nextLink);
            }
        }

        pagination.querySelectorAll('a').forEach((pageLink, i) => {
            if (!pageLink.innerHTML.includes('&#')) {
                pageLink.addEventListener('click', function (event) {
                    event.preventDefault();
                    loadInquiries(category, i);
                });
            }
        });
    }
});

