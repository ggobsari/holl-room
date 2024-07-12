document.addEventListener('DOMContentLoaded', function () {
    let currentPage = 0;
    const pageSize = 20;
    let allData = []; // 모든 데이터를 저장할 배열
    let filteredData = []; // 필터링된 데이터를 저장할 배열

    const postList = document.getElementById('post-list');
    const pagination = document.querySelector('.my-paging');
    const categorySelect = document.getElementById('category');

    // 모든 데이터를 한 번에 가져옴
    fetch(`/hollroom/mypage/myCommunityList?category=all`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            if (Array.isArray(data)) {
                allData = data; // 모든 데이터를 저장
                // 기본 카테고리는 'all'
                filterData('all');
                renderInquiries(filteredData.slice(0, pageSize), filteredData.length, currentPage);
                renderPagination(Math.ceil(filteredData.length / pageSize), currentPage);
            } else {
                console.error('Error: Expected array but received:', data);
            }
        })
        .catch(error => console.error('Error fetching inquiries:', error));

    // 카테고리 변경 시 호출되는 함수
    window.changeCategory = function () {
        const category = categorySelect.value;
        currentPage = 0; // 카테고리 변경 시 페이지를 초기화
        filterData(category);
        renderInquiries(filteredData.slice(0, pageSize), filteredData.length, currentPage);
        renderPagination(Math.ceil(filteredData.length / pageSize), currentPage);
    };

    function filterData(category) {
        if (category === 'all') {
            filteredData = allData;
        } else {
            filteredData = allData.filter(post => post.category === category);
        }
    }

    function renderInquiries(inquiries, totalPosts, currentPage) {
        postList.innerHTML = inquiries.map((board, index) => `
            <tr>
                <td>${totalPosts - (currentPage * pageSize + index)}</td>
                <td class="title-limit">
                    <a href="/hollroom/community/read?postId=${board.post_Id}&action=READ">${board.title}</a>
                </td>
                <td>${board.category}</td>
                <td>${board.created_At ? board.created_At.split('T')[0] : ''}</td>
                <td>${board.view_Count}</td>
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
                    renderInquiries(filteredData.slice(currentPage * pageSize, (currentPage + 1) * pageSize), filteredData.length, currentPage);
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
                    renderInquiries(filteredData.slice(currentPage * pageSize, (currentPage + 1) * pageSize), filteredData.length, currentPage);
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
                    renderInquiries(filteredData.slice(currentPage * pageSize, (currentPage + 1) * pageSize), filteredData.length, currentPage);
                    renderPagination(totalPages, currentPage);
                });
                pagination.appendChild(nextLink);
            }
        }
    }
});

