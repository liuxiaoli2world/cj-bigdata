import styled from 'styled-components';

const Wrapper = styled.div`
    margin-right: 20px;
    background: white;
    .head {
        text-align: right;
    }
    .add-btn {
        margin: 0 0 30px;
    }

    .table-btn {
        width: 70px;
        height: 30px;
        border: 1px solid #ddd;
        margin: 10px;
        background: #eee;
        border-radius: 0;        
        &:hover{
            background:#f1f9ff;
            border-color:#83c4f3;
            color:#3d95d5;
        }
    }
`

export default Wrapper;