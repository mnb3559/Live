import {
  createBrowserRouter,
  createRoutesFromElements,
  Route,
} from "react-router-dom";
import App from "../App";
import HouseDetail from "../components/HouseDetail";
import HouseRegist from "../components/HouseRegist";
import HouseView from "../components/HouseView";
import MyAlert from "../components/MyAlert";
import MyConsulting from "../components/MyConsulting";
import MyContract from "../components/MyContract";
import MyContractDetail from "../components/MyContractDetail";
import MyContractRegist from "../components/MyContractRegist";
import MyContractView from "../components/MyContractView";
import MyInfoDetailRealtor from "../components/MyInfoDetailRealtor";
import MyInfoDetailUser from "../components/MyInfoDetailUser";
import MyInfoModifyRealtor from "../components/MyInfoModifyRealtor";
import MyInfoModifyUser from "../components/MyInfoModifyUser";
import ConsultingPage from "../pages/ConsultingPage";
import ErrorCommonPage from "../pages/ErrorCommonPage";
import HousePage from "../pages/HousePage";
import LoginPage from "../pages/LoginPage";
import MainPage from "../pages/MainPage";
import MyPage from "../pages/MyPage";
import ReservationPage from "../pages/ReservationPage";
import SignUpPage from "../pages/SignUpPage";

const router = createBrowserRouter(
  createRoutesFromElements(
    <Route path="/" element={<App />} errorElement={<ErrorCommonPage />}>
      <Route index element={<MainPage />}></Route>
      <Route path="login" element={<LoginPage />}></Route>
      <Route path="signup" element={<SignUpPage />}></Route>
      <Route path="reservation" element={<ReservationPage />}></Route>
      <Route path="consulting" element={<ConsultingPage />}></Route>
      <Route path="house" element={<HousePage />}>
        <Route index element={<HouseView />}></Route>
        <Route path="regist" element={<HouseRegist />}></Route>
        <Route path="detail" element={<HouseDetail />}></Route>
      </Route>
      <Route path="mypage" element={<MyPage />}>
        <Route path="info-detail-user" element={<MyInfoDetailUser />}></Route>
        <Route path="info-modify-user" element={<MyInfoModifyUser />}></Route>
        <Route
          path="info-detail-realtor"
          element={<MyInfoDetailRealtor />}
        ></Route>
        <Route
          path="info-modify-realtor"
          element={<MyInfoModifyRealtor />}
        ></Route>
        <Route path="consulting" element={<MyConsulting />}></Route>
        <Route path="contract" element={<MyContract />}>
          <Route index element={<MyContractView />}></Route>
          <Route path="regist" element={<MyContractRegist />}></Route>
          <Route path="detail" element={<MyContractDetail />}></Route>
        </Route>
        <Route path="alert" element={<MyAlert />}></Route>
        {/* 부동산 아저씨 일정관리 */}
      </Route>
    </Route>
  )
);

export default router;
