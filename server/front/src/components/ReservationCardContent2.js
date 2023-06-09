import { NavLink, useNavigate } from "react-router-dom";
import classes from "./ReservationCardContent2.module.scss";
import { changeReservationStatus } from "../apis/reservationApis";

const ReservationCardContent2 = ({
  realtoroffice,
  realtorname,
  consultingdate,
  consultinglocation,
  onDetailReservationHandler,
  onChangeReservationHandler,
  tabActive,
  userInfo,
  consultingDate,
  consultingNo,
  itemCount,
  personalInfo,
  image,
  name,
  realtorNo,
  representativeItem,
  status,
  userNo,
  idx,
  link,
}) => {
  const navigation = useNavigate();
  const onDetailHandler = () => {
    navigation(`../user-reservation-detail/${consultingNo}`);
  };

  const goConsultingPage = () => {
    navigation(link);
  };

  return (
    <div className={classes.content}>
      <div className={classes.leftContent}>
        <div className={classes.leftImg}>
          <img src={image} />
        </div>
        <div className={classes.rightDesc}>
          <div className={classes.personalInfo}>
            <p>
              {personalInfo}
              <br />
              <strong>{name}</strong>
            </p>
          </div>
          <div className={classes.consultingInfo}>
            <div className={classes.consultingdate}>
              <p>
                상담 일시
                <br />
                <strong>{consultingDate.substring(0, 10)}</strong>
              </p>
            </div>
            <div className={classes.consultinglocation}>
              <p>
                상담 매물
                <br />
                <strong>
                  {itemCount === 0
                    ? representativeItem
                    : representativeItem + " 외 " + itemCount + "건"}
                </strong>
              </p>
            </div>
          </div>
        </div>
      </div>
      <div className={classes.rightContent}>
        {tabActive === 0 && (
          <div>
            <button className={classes.btn1} onClick={onDetailHandler}>
              예약 상세보기
            </button>
            {status === 1 && (
              <button
                className={classes.btn3}
                onClick={(e) => {
                  onChangeReservationHandler(2, e, consultingNo);
                }}
              >
                예약 확정하기
              </button>
            )}
            <button
              className={classes.btn0}
              onClick={(e) => {
                onChangeReservationHandler(5, e, consultingNo);
              }}
            >
              예약 취소하기
            </button>
          </div>
        )}
        {tabActive === 1 && (
          <div>
            <button className={classes.btn1} onClick={onDetailHandler}>
              예약 상세보기
            </button>
            {link && (
              <button onClick={goConsultingPage} className={classes.btn2}>
                상담 바로가기
              </button>
            )}
          </div>
        )}
        {tabActive === 2 && (
          <div>
            <button className={classes.btn4} onClick={onDetailHandler}>
              예약 상세보기
            </button>
          </div>
        )}
      </div>
    </div>
  );
};

export default ReservationCardContent2;