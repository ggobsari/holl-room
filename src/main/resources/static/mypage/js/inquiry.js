document.addEventListener('DOMContentLoaded', function () {
    //글쓰기 버튼 활성화
    document.getElementById('write-button').addEventListener('click', function () {
        window.location.href = "http://localhost:8090/hollroom/mypage/inquiry_write";
    })
    //==============================================================================================================
    let category = 'all';
    let currentPage = 0;
    const pageSize = 20;  // 한 페이지당 포스트 수

    const postList = document.getElementById('inquiry-table-body');
    const pagination = document.querySelector('.my-paging');

    loadInquiries(category, currentPage);

    function loadInquiries(category, page) {
        //서버에서 문의목록 데이터 가져오기
        fetch(`/hollroom/mypage/inquiryposts?category=${category}&page=${page}&size=${pageSize}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                if (Array.isArray(data.posts)) {
                    renderInquiries(data.posts, data.totalPosts, page);
                    renderPagination(data.totalPages, page);
                } else {
                    console.error('Error: Expected array but received:', data.posts);
                }
            })
            .catch(error => console.error('Error fetching inquiries:', error));
    }
    //문의글 목록 삽입
    function renderInquiries(inquiries, totalPosts, currentPage) {
        postList.innerHTML = inquiries.map((inquiry, index) => `
            <tr>
                <td>${totalPosts - (currentPage * pageSize + index)}</td>
                <td class="write-limit"><a href="/hollroom/mypage/inquiry/${inquiry.postId}">${inquiry.title}</a></td>
                <td>${inquiry.createdAt.split(' ')[0]}</td>
                <td>${inquiry.answerContent ? 'O' : ''}</td>
                <td>${inquiry.answerAt ? inquiry.answerAt.split('T')[0] : ''}</td><!--표기 오류날수 있음-->
            </tr>
        `).join('');
    }
    //페이지네이션
    function renderPagination(totalPages, currentPage) {
        pagination.innerHTML = '';

        if (totalPages > 1) {
            // 현 페이지수가 3페이지보다 많을 경우 이전 페이지 링크 추가
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
            //페이지 번호 링크 삽입
            const pages = Array.from({length: totalPages}, (_, i) => `
                <a href="#" class="${i === currentPage ? 'active' : ''}">${i + 1}</a>
            `).join('');

            pagination.innerHTML += pages;
            //총 페이지수가 3보다 클 경우 다음 페이지 링크 추가
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

