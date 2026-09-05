<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<jsp:include page="/views/decorators/admin-header.jsp" />

<div class="d-flex justify-content-between align-items-center mb-4 bg-white p-3 rounded-3 shadow-sm border-start border-4 border-primary">
    <div>
        <h4 class="fw-bold text-dark m-0"><i class="bi bi-box-seam-fill text-primary me-2"></i>Quản Lý Sản Phẩm</h4>
        <small class="text-muted">Xem, thêm mới và quản lý danh sách sản phẩm trong kho</small>
    </div>
    <a href="${pageContext.request.contextPath}/admin/product/add" class="btn btn-primary px-4 py-2 rounded-pill shadow-sm fw-semibold">
        <i class="bi bi-plus-lg me-1"></i> Thêm Sản Phẩm
    </a>
</div>

<div class="card border-0 shadow-sm rounded-3 overflow-hidden">
    <div class="card-body p-0">
        <div class="table-responsive">
            <table class="table table-hover align-middle mb-0">
                <thead class="bg-light text-uppercase text-secondary fs-7 fw-bold border-bottom">
                    <tr>
                        <th class="text-center py-3" style="width: 70px;">STT</th>
                        <th class="py-3" style="width: 100px;">Hình Ảnh</th>
                        <th class="py-3">Tên Sản Phẩm</th>
                        <th class="py-3">Giá Bán</th>
                        <th class="py-3 text-center">Tồn Kho</th>
                        <th class="py-3">Danh Mục</th>
                        <th class="py-3 text-center" style="width: 160px;">Thao Tác</th>
                    </tr>
                </thead>
                <tbody class="border-top-0">
                    <c:forEach items="${products != null ? products : listproduct}" var="p" varStatus="stt">
                        <tr>
                            <td class="text-center fw-bold text-secondary">${stt.index + 1}</td>
                            <td>
                                <div class="position-relative">
                                    <c:choose>
                                        <c:when test="${p.images != null && p.images.startsWith('http')}">
                                            <img src="${p.images}" class="rounded-3 border object-fit-cover shadow-sm" width="55" height="55">
                                        </c:when>
                                        <c:otherwise>
                                            <img src="${pageContext.request.contextPath}/image?fname=${p.images}" class="rounded-3 border object-fit-cover shadow-sm" width="55" height="55">
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </td>
                            <td>
                                <div class="fw-bold text-dark mb-1">${p.productname}</div>
                                <small class="text-muted d-block text-truncate" style="max-width: 250px;">${p.description}</small>
                            </td>
                            <td class="fw-bold text-danger">
                                <fmt:formatNumber value="${p.price}" pattern="#,### VNĐ"/>
                            </td>
                            <td class="text-center">
                                <c:choose>
                                    <c:when test="${p.quantity > 10}">
                                        <span class="badge bg-success-subtle text-success px-3 py-2 rounded-pill fw-semibold">${p.quantity} SP</span>
                                    </c:when>
                                    <c:when test="${p.quantity > 0}">
                                        <span class="badge bg-warning-subtle text-warning px-3 py-2 rounded-pill fw-semibold">${p.quantity} SP</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-danger-subtle text-danger px-3 py-2 rounded-pill fw-semibold">Hết hàng</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <span class="badge bg-light text-dark border px-3 py-2 rounded-2 fw-normal">${p.category.categoryname}</span>
                            </td>
                            <td class="text-center">
                                <div class="btn-group" role="group">
                                    <a href="${pageContext.request.contextPath}/admin/product/edit?id=${p.productid}" class="btn btn-outline-primary btn-sm rounded-2 me-1" title="Chỉnh sửa">
                                        <i class="bi bi-pencil-fill"></i>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/admin/product/delete?id=${p.productid}"
                                       onclick="return confirm('Bạn có chắc chắn muốn xóa sản phẩm này?')"
                                       class="btn btn-outline-danger btn-sm rounded-2" title="Xóa">
                                        <i class="bi bi-trash-fill"></i>
                                    </a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty products && empty listproduct}">
                        <tr>
                            <td colspan="7" class="text-center py-5 text-muted">
                                <i class="bi bi-inbox fs-1 d-block mb-2 text-secondary"></i>
                                Chưa có sản phẩm nào trong cơ sở dữ liệu.
                            </td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="/views/decorators/admin-footer.jsp" />