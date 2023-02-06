import Button from "../../UI/Button";
import DateSelector from "./DateSelector";
import SelectBox from "../../UI/SelectBox";
import { useRef } from "react";

import classes from "./ReservationSearchBox.module.scss";

const dummyMaker = (str) => {
  return [
    { name: `${str}이름 1`, value: `${str} value 1` },
    { name: `${str}이름 2`, value: `${str} value 2` },
    { name: `${str}이름 3`, value: `${str} value 3` },
  ];
};

const ReservationSearchBox = (props) => {
  const sido = useRef("");
  const gugun = useRef("");
  const dong = useRef("");
  const refDate = useRef(new Date());

  const regionsSido = "/regions/sidos로 얻은 데이터";
  const regionsGugun = "/regions/guguns로 얻은 데이터";
  const regionsDong = "/regions/dongs로 얻은 데이터";

  const changeEventHandler = (e, refs) => {
    console.log(e);
    if (e.target) {
      refs.current = e.target.value;
    } else {
      refs.current = e;
    }
  };

  return (
    <div className={classes.searchBox}>
      {/* <p>안녕 나는 검색상자</p> */}
      <div>
        <SelectBox
          dataArray={dummyMaker("시")}
          default={"시를 선택해 주세요"}
          changeEventHandler={(e) => {
            changeEventHandler(e, sido);
          }}
        />
        <SelectBox
          dataArray={dummyMaker("구")}
          default={"구를 선택해 주세요"}
          changeEventHandler={(e) => {
            changeEventHandler(e, gugun);
          }}
        />
        <SelectBox
          dataArray={dummyMaker("동")}
          default={"동을 선택해 주세요"}
          changeEventHandler={(e) => {
            changeEventHandler(e, dong);
          }}
        />

        <DateSelector
          changeEventHandler={(e) => {
            changeEventHandler(e, refDate);
          }}
        />
      </div>
      <Button
        clickEvent={() =>
          props.clickSearchEventHandler(
            sido.current,
            gugun.current,
            dong.current,
            refDate.current
          )
        }
      >
        적용
      </Button>
    </div>
  );
};

export default ReservationSearchBox;