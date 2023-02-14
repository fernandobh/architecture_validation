import http from 'k6/http';
import { check } from 'k6';
import { Trend,Rate } from 'k6/metrics';

const listAllErrorRate = new Rate('RATE List errors');
const createErrorRate = new Rate('RATE Create errors');
const listOneErrorRate = new Rate('RATE List One errors');

const ListAllTrend = new Trend('Trend List');
const CreateTrend = new Trend('Trend Create');
const ListOneTrend = new Trend('Trend List One');

export default function () {
  const url = 'http://localhost:9000/client';

  //List All
  var resp = http.get(url);
  check(resp, {
    'ALL is status 200': (r) => r.status === 200,
  }) || listAllErrorRate.add(1);

  ListAllTrend.add(resp.timings.duration);

  //CREATE
  const data = JSON.stringify({
    firstName: `Name ${__VU}: ${__ITER}`,
    lastName: `Last Name ${__VU}: ${__ITER}`,
  });
  var resp_create = http.post(url, data, {
    headers: { 'Content-Type': 'application/json' },
  });
  check(resp_create, {
    'CREATE status is 201': (r) => r.status == 201,
  }) || createErrorRate.add(1);
  CreateTrend.add(resp_create.timings.duration);

  //LIST ONE
  if(resp_create.status == 201){
    const obj = resp_create.json();
    var resp_one = http.get(url+"/"+obj.id);
    check(resp_one, {
        'ONE is status 200': (r) => r.status === 200,
    })|| listOneErrorRate.add(1);
    ListOneTrend.add(resp_one.timings.duration);
  }

}