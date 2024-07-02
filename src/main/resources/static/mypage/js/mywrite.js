document.addEventListener('DOMContentLoaded', function () {
    // 초기 카테고리를 설정하거나 'all'로 기본값을 설정
    let category = document.getElementById('category').dataset.category || 'all';
    let currentPage = 0; // 현재 페이지를 0으로 초기화

    const categorySelect = document.getElementById('category');
    const postList = document.getElementById('post-list');
    const pagination = document.querySelector('.my-paging');

    if (categorySelect) {
        // 초기 카테고리 값을 설정
        categorySelect.value = category;

        // 카테고리 변경 시 이벤트 리스너 설정
        categorySelect.addEventListener('change', function () {
            currentPage = 0; // 카테고리 변경 시 페이지를 0으로 초기화
            loadPosts(this.value, currentPage); // 새로운 카테고리에 따른 게시글 로드
        });
    } else {
        console.error("카테고리 선택 요소를 찾을 수 없습니다.");
    }

    // 초기 로딩 시 게시글 목록 불러오기
    loadPosts(category, currentPage);

    // 주어진 카테고리와 페이지 번호에 따른 게시글을 서버에서 가져옴
    function loadPosts(category, page) {
        fetch(`mypost?category=${category}&page=${page}`)
            .then(response => response.json())
            .then(data => {
                if (Array.isArray(data.posts)) {
                    renderPosts(data.posts); // 게시글 목록을 렌더링
                    renderPagination(data.totalPages, page); // 페이지 네비게이션을 렌더링
                } else {
                    console.error('Error: Expected array but received:', data.posts);
                }
            })
            .catch(error => console.error('Error fetching posts:', error));
    }

    // 서버에서 가져온 게시글 데이터를 HTML로 변환하여 화면에 표시
    function renderPosts(posts) {
        postList.innerHTML = posts.map(post => `
            <tr>
                <td>${post.postId}</td>
                <td><a href="/hollroom/community/read?postId=${post.postId}&action=READ">${post.title}</a></td>
                <td>${post.category}</td>
                <td>${post.updatedAt.split(' ')[0]}</td>
                <td>${post.viewCount}</td>
            </tr>
        `).join('');
    }

    // 페이지 네비게이션을 렌더링
    function renderPagination(totalPages, currentPage) {
        pagination.innerHTML = ''; // 기존의 페이지 네비게이션을 초기화

        if (totalPages > 1) {
            // 현재 페이지가 3보다 클 때 이전 화살표를 추가
            if (currentPage > 3) {
                const prevLink = document.createElement('a');
                prevLink.href = '#';
                prevLink.innerHTML = '&#5130;'; // 이전 화살표
                prevLink.addEventListener('click', function (event) {
                    event.preventDefault();
                    loadPosts(category, currentPage - 1); // 이전 페이지로 이동
                });
                pagination.appendChild(prevLink);
            }

            // 페이지 번호를 추가
            const pages = Array.from({ length: totalPages }, (_, i) => `
                <a href="#" class="${i === currentPage ? 'active' : ''}">${i + 1}</a>
            `).join('');

            pagination.innerHTML += pages;

            // 총 페이지가 3보다 클 때 다음 화살표 추가
            if (totalPages > 3) {
                const nextLink = document.createElement('a');
                nextLink.href = '#';
                nextLink.innerHTML = '&#5125;'; // 다음 화살표
                nextLink.addEventListener('click', function (event) {
                    event.preventDefault();
                    loadPosts(category, currentPage + 1); // 다음 페이지로 이동
                });
                pagination.appendChild(nextLink);
            }
        }

        // 각 페이지 링크에 클릭 이벤트 리스너를 추가
        pagination.querySelectorAll('a').forEach((pageLink, i) => {
            if (!pageLink.innerHTML.includes('&#')) { // 화살표가 아닌 페이지 번호만 처리
                pageLink.addEventListener('click', function (event) {
                    event.preventDefault();
                    loadPosts(category, i); // 클릭된 페이지로 이동
                });
            }
        });
    }
});
