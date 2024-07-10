document.addEventListener('DOMContentLoaded', function () {
    let category = 'all';
    let currentPage = 0;
    const pageSize = 20;
    let allData = []; // 모든 데이터를 저장할 배열

    const postList = document.getElementById('post-list');
    const pagination = document.querySelector('.my-paging');

    // 처음에 모든 데이터를 한 번에 가져옴
    fetch(`/hollroom/mypage/myRoommateList?category=${category}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            if (Array.isArray(data)) {
                allData = data; // 모든 데이터를 저장
                renderInquiries(data.slice(0, pageSize), data.length, currentPage);
                renderPagination(Math.ceil(data.length / pageSize), currentPage);
            } else {
                console.error('Error: Expected array but received:', data);
            }
        })
        .catch(error => console.error('Error fetching inquiries:', error));

    function renderInquiries(inquiries, totalPosts, currentPage) {
        postList.innerHTML = inquiries.map((board, index) => `
            <tr>
                <td>${totalPosts - (currentPage * pageSize + index)}</td>
               <td class="title-limit">
                    <a href="/roommate/detail?roommate_id=${board.roommate_id}&action=READ">${board.title}</a>
                </td>
                <td class="write-limit">${board.content}</td>
                <td>${board.created_at ? board.created_at.split('T')[0] : ''}</td>
            </tr>
        `).join('');
    }

    function renderPagination(totalPages, currentPage) {
        pagination.innerHTML = '';

        if (totalPages > 1) {
            if (currentPage > 0) {
                const prevLink = document.createElement('a');
                prevLink.href = '#';
                prevLink.innerHTML = '&#5130;';
                prevLink.addEventListener('click', function (event) {
                    event.preventDefault();
                    currentPage--;
                    renderInquiries(allData.slice(currentPage * pageSize, (currentPage + 1) * pageSize), allData.length, currentPage);
                    renderPagination(totalPages, currentPage);
                });
                pagination.appendChild(prevLink);
            }

            for (let i = 0; i < totalPages; i++) {
                const pageLink = document.createElement('a');
                pageLink.href = '#';
                pageLink.className = i === currentPage ? 'active' : '';
                pageLink.innerText = i + 1;
                pageLink.addEventListener('click', function (event) {
                    event.preventDefault();
                    currentPage = i;
                    renderInquiries(allData.slice(currentPage * pageSize, (currentPage + 1) * pageSize), allData.length, currentPage);
                    renderPagination(totalPages, currentPage);
                });
                pagination.appendChild(pageLink);
            }

            if (currentPage < totalPages - 1) {
                const nextLink = document.createElement('a');
                nextLink.href = '#';
                nextLink.innerHTML = '&#5125;';
                nextLink.addEventListener('click', function (event) {
                    event.preventDefault();
                    currentPage++;
                    renderInquiries(allData.slice(currentPage * pageSize, (currentPage + 1) * pageSize), allData.length, currentPage);
                    renderPagination(totalPages, currentPage);
                });
                pagination.appendChild(nextLink);
            }
        }
    }
});