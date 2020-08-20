import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './horaire.reducer';
import { IHoraire } from 'app/shared/model/horaire.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IHoraireDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const HoraireDetail = (props: IHoraireDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { horaireEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="intranetApp.horaire.detail.title">Horaire</Translate> [<b>{horaireEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="idHoraire">
              <Translate contentKey="intranetApp.horaire.idHoraire">Id Horaire</Translate>
            </span>
          </dt>
          <dd>{horaireEntity.idHoraire}</dd>
          <dt>
            <span id="heureDepart">
              <Translate contentKey="intranetApp.horaire.heureDepart">Heure Depart</Translate>
            </span>
          </dt>
          <dd>{horaireEntity.heureDepart}</dd>
          <dt>
            <span id="heureF">
              <Translate contentKey="intranetApp.horaire.heureF">Heure F</Translate>
            </span>
          </dt>
          <dd>{horaireEntity.heureF}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="intranetApp.horaire.date">Date</Translate>
            </span>
          </dt>
          <dd>{horaireEntity.date ? <TextFormat value={horaireEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/horaire" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/horaire/${horaireEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ horaire }: IRootState) => ({
  horaireEntity: horaire.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(HoraireDetail);
