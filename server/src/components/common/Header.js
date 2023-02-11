import { NavLink } from "react-router-dom";
import classes from "./Header.module.scss";
import logo from "../../assets/image/liveLogo.png";
import { makeUUID } from "../../util/UUID";
import { useEffect, useState } from "react";

import { GiHamburgerMenu } from "react-icons/gi";
import { useDispatch, useSelector } from "react-redux";
import { useAuth } from "./AuthProtector";
import { userAction } from "../../store/user-slice";

const Header = () => {
  const [isToggled, setIsToggled] = useState(false);

  const isToggledHandler = () => {
    setIsToggled(!isToggled);
  };

  const { userInfo, doLogin, doLogout } = useAuth();
  const dispatch = useDispatch();

  const logoutEventHandler = (e) => {
    e.preventDefault();
    console.log("ㄱㄱ");

    if (isToggled) isToggledHandler();

    doLogout();
  };

  return (
    <div className={classes.header}>
      <div className={classes.headerInner}>
        <div className={classes.item}>
          <NavLink to={"/"} onClick={isToggled && isToggledHandler}>
            <img src={logo} alt="Live Logo" />
          </NavLink>
          <button className={classes.toggleBtn}>
            <GiHamburgerMenu onClick={isToggledHandler} />
          </button>
        </div>
        <ul className={`${classes["menu"]} ${isToggled && classes.active}`}>
          {userInfo.isRealtor ? (
            <li>
              <NavLink to={"/house"} onClick={isToggled && isToggledHandler}>
                매물
              </NavLink>{" "}
            </li>
          ) : (
            ""
          )}
          <li>
            <NavLink
              to={"/reservation"}
              onClick={isToggled && isToggledHandler}
            >
              예약
            </NavLink>
          </li>
          <li>
            <NavLink
              to={`/consulting/${makeUUID()}`}
              onClick={isToggled && isToggledHandler}
            >
              상담
            </NavLink>
          </li>
          <li>
            <NavLink to={"/mypage"} onClick={isToggled && isToggledHandler}>
              마이페이지
            </NavLink>
          </li>
          {userInfo.id ? (
            <li>
              <NavLink onClick={logoutEventHandler}>로그아웃</NavLink>
            </li>
          ) : (
            ""
          )}{" "}
          {userInfo.id ? (
            ""
          ) : (
            <li>
              <NavLink to={"/login"} onClick={isToggled && isToggledHandler}>
                로그인
              </NavLink>
            </li>
          )}
          {userInfo.id ? (
            ""
          ) : (
            <li>
              <NavLink to={"/signup"} onClick={isToggled && isToggledHandler}>
                회원가입
              </NavLink>
            </li>
          )}
          <li>
            <NavLink to={"/alert"} onClick={isToggled && isToggledHandler}>
              알람
            </NavLink>
          </li>
          <li>
            <NavLink to={"/contract"} onClick={isToggled && isToggledHandler}>
              계약
            </NavLink>
          </li>
        </ul>
      </div>
    </div>
  );
};

export default Header;